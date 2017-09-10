package com.johnhite.foo.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.johnhite.bar.client.BarClient;
import com.johnhite.foo.api.Foo;

@Path("/foo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FooResource {
	private static final Logger logger = LoggerFactory.getLogger(FooResource.class);
	
	private final BarClient bar;

	@Inject 
	public FooResource(BarClient bar) {
		this.bar = bar;
	}
	
	@Timed
	@GET
	public Response getFoo() {
		logger.info("Foo: [get Foo]");
		return Response.ok(new Foo()).build();
	}
	
	@Timed
	@GET
	@Path("/bar")
	public Response getBar() {
		logger.info("Foo: [get Bar]");
		return Response.ok(bar.getBar()).build();
	}
	
}
