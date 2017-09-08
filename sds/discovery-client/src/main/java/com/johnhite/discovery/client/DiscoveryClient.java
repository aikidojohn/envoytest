package com.johnhite.discovery.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.johnhite.discovery.api.Host;
import com.johnhite.discovery.api.HostArray;

public class DiscoveryClient {

	private final Client client;
	private final URI uri;
	public DiscoveryClient(String baseUri, Client client) {
		this.client = client;
		try {
			uri = new URI(baseUri);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Malformed URL", e);
		}
	}
	
	public Response register(String service, Host host) {
		return client.target(uri)
			.path("/v1/registration/" + service)
			.request(MediaType.APPLICATION_JSON)
			.post(Entity.json(host));
	}
	
	public Response unregister(String service, Host host) {
		return client.target(uri)
			.path("/v1/registration/" + service + "/" + host.getIpAddress() + "/" + host.getPort())
			.request(MediaType.APPLICATION_JSON)
			.delete();
	}
	
	public List<Host> getHosts(String service) { 
		HostArray array = client.target(uri)
				.path("/v1/registration/" + service)
				.request(MediaType.APPLICATION_JSON)
				.get(HostArray.class);
		return Arrays.asList(array.getHosts());
	}
}
