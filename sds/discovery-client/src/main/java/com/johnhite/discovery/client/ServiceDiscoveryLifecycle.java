package com.johnhite.discovery.client;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.johnhite.discovery.api.Host;

import io.dropwizard.lifecycle.Managed;

public class ServiceDiscoveryLifecycle implements Managed {
	private static final Logger logger = LoggerFactory.getLogger(ServiceDiscoveryLifecycle.class);
	private final DiscoveryClient client;
	private Host host;
	private final String cluster;
	public ServiceDiscoveryLifecycle(DiscoveryClient client, Host host, String cluster) {
		this.client = client;
		this.host = host;
		this.cluster = cluster;
	}
	@Override
	public void start() throws Exception {
		logger.info("Registering {} cluster node {}:{} with discovery service", cluster, host.getIpAddress() == null ? "[auto]" : host.getIpAddress(), host.getPort());
		Response resp = client.register(cluster, host);
		if (resp.getStatus() == 200) {
			this.host = resp.readEntity(Host.class);
			logger.info("Registered {} cluster node {}:{}", cluster, host.getIpAddress(), host.getPort());
		}
	}

	@Override
	public void stop() throws Exception {
		logger.info("Removing {} cluster node {}:{} from discovery service", cluster, host.getIpAddress(), host.getPort());
		client.unregister(cluster, host);	
	}

}
