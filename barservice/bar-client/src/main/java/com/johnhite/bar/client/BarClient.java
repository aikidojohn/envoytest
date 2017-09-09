package com.johnhite.bar.client;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;

import com.johnhite.bar.api.Bar;

public class BarClient {

	private final Client client;
	private final URI uri;
	public BarClient(String baseUri, Client client) {
		this.client = client;
		try {
			uri = new URI(baseUri);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Malformed URL", e);
		}
	}
	
	public Bar getBar() { 
		return client.target(uri)
				.path("/bar")
				.request(MediaType.APPLICATION_JSON)
				.get(Bar.class);
	}
}
