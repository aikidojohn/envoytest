package com.johnhite.discovery.client;

import javax.ws.rs.client.Client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.johnhite.discovery.api.Host;
import com.johnhite.discovery.client.DiscoveryEnabledConfiguration.DiscoveryServiceConfiguration;

import io.dropwizard.ConfiguredBundle;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.jetty.ConnectorFactory;
import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.jetty.HttpsConnectorFactory;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DiscoveryBundle<T extends DiscoveryEnabledConfiguration> implements ConfiguredBundle<T> {
	private static final Logger logger = LoggerFactory.getLogger(DiscoveryBundle.class);
			
	@Override
	public void initialize(Bootstrap<?> bootstrap) {
		// TODO Auto-generated method stub
		
	}

	private static enum ConnType {
		NONE(-1), HTTP(0), H2C(1), HTTPS(2), H2(3);
		
		private int ordinal;
		private ConnType(int ordinal) {
			this.ordinal = ordinal;
		}
		
		public int getOrdinal() {
			return ordinal;
		}
	}

	@Override
	public void run(DiscoveryEnabledConfiguration config, Environment env) throws Exception {
		DiscoveryServiceConfiguration dsconfig = config.getDiscoveryConfig();
		Host.Tags tags = new Host.Tags(dsconfig.getAz(), dsconfig.isCanary(), dsconfig.getLoadBalancingWeight());
		Host host = null;
		
		// Static configuration
		if (dsconfig.getIp() != null) {
			host = new Host(dsconfig.getIp(), dsconfig.getPort(), tags);
			logger.info("[Discovery] Found node configuration {}:{}", dsconfig.getIp(), dsconfig.getPort());
		}
		// Dynamic config via application connectors
		else if (config.getServerFactory() instanceof DefaultServerFactory) {
			DefaultServerFactory factory = (DefaultServerFactory)config.getServerFactory();
			
			ConnType bestType = ConnType.NONE;
			HttpConnectorFactory best = null;
			for (ConnectorFactory connector : factory.getApplicationConnectors()) {
				if ("io.dropwizard.http2.Http2ConnectorFactory".equals(connector.getClass().getName())) {
					if (bestType.getOrdinal() < ConnType.H2.getOrdinal()) {
						bestType = ConnType.H2;
						best = (HttpConnectorFactory)connector;
					}
				}
				else if ("io.dropwizard.http2.Http2CConnectorFactory".equals(connector.getClass().getName())) {
					if (bestType.getOrdinal() < ConnType.H2C.getOrdinal()) {
						bestType = ConnType.H2C;
						best = (HttpConnectorFactory)connector;
					}
				}
				else if (connector instanceof HttpsConnectorFactory) {
					if (bestType.getOrdinal() < ConnType.HTTPS.getOrdinal()) {
						bestType = ConnType.HTTPS;
						best = (HttpConnectorFactory)connector;
					}
				}
				else if (connector instanceof HttpConnectorFactory) {
					if (bestType.getOrdinal() < ConnType.HTTP.getOrdinal()) {
						bestType = ConnType.HTTP;
						best = (HttpConnectorFactory)connector;
					}
				}
			}
			if (bestType != ConnType.NONE) {
				String bindHost = best.getBindHost();
				int port = best.getPort();
				host = new Host(bindHost, port, tags);
				logger.info("[Discovery] Choosing {} connector at {}:{}", bestType.name(), bindHost == null ? "[auto]" : bindHost, port);
			}
		}
		if (host == null) {
			logger.error("[Discovery] Failed to find node configuration to register with discovery service.");
			throw new RuntimeException("Invalid discovery configuration. Stopping.");
		}
		
		final Client jerseyClient = new JerseyClientBuilder(env).using(dsconfig).build("sds");
		final DiscoveryClient client = new DiscoveryClient(dsconfig.getUrl(), jerseyClient);
		env.lifecycle().manage(new ServiceDiscoveryLifecycle(client, host, dsconfig.getCluster()));
	}

}
