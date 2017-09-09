package com.johnhite.bar.client;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johnhite.bar.client.BarClient;

public class BarClientTest {

	@Ignore("This test requires a server running on localhost. Just used to execrise the server a bit")
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
		JerseyClient jerseyClient = new JerseyClientBuilder()
				.hostnameVerifier( (a, b) -> true)
				.sslContext(sc)
				.build();
		BarClient client = new BarClient("https://localhost:6443/", jerseyClient);
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(client.getBar()));
	}
}
