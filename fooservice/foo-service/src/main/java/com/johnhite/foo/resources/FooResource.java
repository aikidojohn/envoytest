package com.johnhite.foo.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.johnhite.foo.api.Foo;

@Path("/foo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FooResource {
	private static final Logger logger = LoggerFactory.getLogger(FooResource.class);
	
	//@Inject 
	public FooResource() {
	}
	
	@Timed
	@GET
	@Path("/")
	public Response getFoo() {
		return Response.ok(new Foo()).build();
	}
	
}
