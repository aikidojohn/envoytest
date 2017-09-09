package com.johnhite.discovery.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.johnhite.discovery.api.Host;

import io.dropwizard.lifecycle.Managed;

public class ServiceDiscoveryLifecycle implements Managed {
	private static final Logger logger = LoggerFactory.getLogger(ServiceDiscoveryLifecycle.class);
	private final DiscoveryClient client;
	private final Host host;
	private final String cluster;
	public ServiceDiscoveryLifecycle(DiscoveryClient client, Host host, String cluster) {
		this.client = client;
		this.host = host;
		this.cluster = cluster;
	}
	@Override
	public void start() throws Exception {
		logger.info("Registering {} cluster node {}:{} with discovery service", cluster, host.getIpAddress(), host.getPort());
		client.register(cluster, host);
	}

	@Override
	public void stop() throws Exception {
		logger.info("Removing {} cluster node {}:{} from discovery service", cluster, host.getIpAddress(), host.getPort());
		client.unregister(cluster, host);	
	}

}
