package com.johnhite.discovery.resources;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.johnhite.discovery.api.Host;
import com.johnhite.discovery.storage.Storage;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DiscoveryV1Resource {
	private static final Logger logger = LoggerFactory.getLogger(DiscoveryV1Resource.class);
	private Storage storage;
	
	@Inject 
	public DiscoveryV1Resource(Storage storage) {
		this.storage = storage;
	}
	
	@Timed
	@GET
	@Path("registration/{serviceName}")
	public Response getRegistration(@PathParam("serviceName") String serviceName) {
		return Response.ok(storage.getHosts(serviceName)).build();
	}
	
	@Timed
	@POST
	@Path("registration/{serviceName}")
	public Response getRegistration(@PathParam("serviceName") String serviceName, Host host, @Context HttpServletRequest req) {
		if (host.getIpAddress() == null) {
			logger.info("Host address missing in request body.");
			String forward = req.getHeader("X-Forwarded-For");
			String remoteIp = null;
			if (forward != null) {
				remoteIp = forward.split(",")[0].trim();
				logger.info("Using IP from X-Forwarded-For: {}", remoteIp);
			}
			else {
				remoteIp = req.getRemoteAddr();
				logger.info("Using connection remote IP: {}", remoteIp);
			}
			host = new Host(remoteIp, host.getPort(), host.getTags());
		}
		storage.storeHost(serviceName, host);
		return Response.ok().build();
	}
	
	@Timed
	@DELETE
	@Path("registration/{serviceName}/{ip}/{port}")
	public Response removeRegistration(@PathParam("serviceName") String serviceName, @PathParam("ip") String ip, @PathParam("port") int port) {
		Host host = new Host(ip, port, new Host.Tags(null, false, 0));
		storage.removeHost(serviceName, host);
		return Response.ok().build();
	}
}
