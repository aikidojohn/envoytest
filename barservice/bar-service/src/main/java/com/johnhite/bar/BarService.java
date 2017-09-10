package com.johnhite.bar;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.codahale.metrics.health.HealthCheck;
import com.johnhite.discovery.client.DiscoveryBundle;
import com.johnhite.bar.resources.BarResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class BarService extends Application<BarServiceConfiguration> {

	@Override
	public void initialize(Bootstrap<BarServiceConfiguration> boot) {
		boot.addBundle(new DiscoveryBundle<BarServiceConfiguration>());
	}
	
	@Override
	public void run(BarServiceConfiguration config, Environment env) throws Exception {
		AbstractBinder binder= new BarServiceBinder(config, env);
		env.jersey().register(binder);
		env.jersey().register(BarResource.class);
		
		env.healthChecks().register("Bar Service Check", new HealthCheck() {
			@Override
			protected Result check() throws Exception {
				return Result.healthy();
			}
		});
	}

	public static void main(String... args) throws Exception {
		new BarService().run(args);
	}
}
