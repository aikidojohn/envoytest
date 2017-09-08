package com.johnhite.discovery.client;

import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johnhite.discovery.api.Host;

public class DiscoveryClientTest {

	@Test
	public void test() throws Exception{
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
		    public X509Certificate[] getAcceptedIssuers(){return null;}
		    public void checkClientTrusted(X509Certificate[] certs, String authType){}
		    public void checkServerTrusted(X509Certificate[] certs, String authType){}
		}};

	    SSLContext sc = SSLContext.getInstance("TLS");
	    sc.init(null, trustAllCerts, new SecureRandom());
	    //HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		
		ObjectMapper mapper = new ObjectMapper();
		JerseyClient client = new JerseyClientBuilder()
				.hostnameVerifier( (a, b) -> true)
				.sslContext(sc)
				.build();
		DiscoveryClient dc = new DiscoveryClient("https://localhost:8443/", client);
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dc.getHosts("test1")));
		
		dc.register("test1", new Host("192.168.1.1", 8080, new Host.Tags("us-east", false, 1)));
		dc.register("test1", new Host("192.168.1.2", 8080, new Host.Tags("us-east", false, 1)));
		dc.register("test1", new Host("192.168.1.3", 8080, new Host.Tags("us-east", false, 2)));
		dc.register("test1", new Host("192.168.1.4", 8080, new Host.Tags("us-east", true, 2)));
		dc.register("test1", new Host(null, 8080, new Host.Tags("us-east", false, 2)));
		
		//Try X-Forwarded-For
		client.target(new URI("https://localhost:8443/"))
				.path("/v1/registration/test1")
				.request(MediaType.APPLICATION_JSON)
				.header("X-Forwarded-For", "10.0.0.6, 127.0.0.1")
				.post(Entity.json( new Host(null, 8080, new Host.Tags("us-east", false, 2)) ));
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dc.getHosts("test1")));
		
		dc.unregister("test1", new Host("192.168.1.2", 8080, new Host.Tags("us-east", false, 1)));
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dc.getHosts("test1")));
	}
}
