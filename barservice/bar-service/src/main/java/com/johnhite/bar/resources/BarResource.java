package com.johnhite.bar.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.johnhite.bar.api.Bar;

@Path("/bar")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BarResource {
	private static final Logger logger = LoggerFactory.getLogger(BarResource.class);
	//private final FooClient foo;
	
	/*@Inject 
	public BarResource(FooClient foo) {
		this.foo = foo;
	}*/
	
	@Timed
	@GET
	public Response getBar() {
		logger.info("Bar: [get Bar]");
		return Response.ok(new Bar()).build();
	}
	
	/*@Timed
	@GET
	@Path("/foo")
	public Response getFoo() {
		logger.info("Bar: [get Foo]");
		return Response.ok(foo.getFoo()).build();
	}*/
}
