package com.johnhite.foo.client;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;

import com.johnhite.foo.api.Foo;

public class FooClient {

	private final Client client;
	private final URI uri;
	public FooClient(String baseUri, Client client) {
		this.client = client;
		try {
			uri = new URI(baseUri);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("Malformed URL", e);
		}
	}
	
	public Foo getFoo() { 
		return client.target(uri)
				.path("/foo")
				.request(MediaType.APPLICATION_JSON)
				.get(Foo.class);
	}
}
