package com.johnhite.discovery.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.WILDCARD)
@Path("/healthcheck")
public class DiscoveryHealthcheckResource {
	private static final Logger logger = LoggerFactory.getLogger("[healthcheck]");
	private volatile boolean draining = false;
	
	@GET
	public Response healthcheck() {
		if (draining) {
			return Response.status(503).entity("{ \"status\": \"DRAINING\"}").build();
		}
		return Response.ok("{ \"status\": \"HEALTHY\"}").build();
	}
	
	@GET
	@Path("/fail")
	public Response drain() {
		this.draining = true;
		logger.warn("Healthcheck set to failed");
		return Response.ok().build();
	}
	
	@GET
	@Path("/ok")
	public Response undrain() {
		this.draining = false;
		logger.warn("Healthcheck set to recovered");
		return Response.ok().build();
	}
	
	
}
