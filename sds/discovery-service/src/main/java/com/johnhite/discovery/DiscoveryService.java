package com.johnhite.discovery;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.codahale.metrics.health.HealthCheck;
import com.johnhite.discovery.resources.DiscoveryV1Resource;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class DiscoveryService extends Application<DiscoveryServiceConfiguration> {

	@Override
	public void run(DiscoveryServiceConfiguration config, Environment env) throws Exception {
		AbstractBinder binder= new DiscoveryServiceBinder();
		env.jersey().register(binder);
		env.jersey().register(DiscoveryV1Resource.class);
		
		env.healthChecks().register("Discovery Service Check", new HealthCheck() {
			@Override
			protected Result check() throws Exception {
				return Result.healthy();
			}
		});
	}

	public static void main(String... args) throws Exception {
		new DiscoveryService().run(args);
	}
}
