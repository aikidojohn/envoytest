package com.johnhite.foo;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.codahale.metrics.health.HealthCheck;
import com.johnhite.discovery.client.DiscoveryBundle;
import com.johnhite.foo.resources.FooResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class FooService extends Application<FooServiceConfiguration> {

	@Override
	public void initialize(Bootstrap<FooServiceConfiguration> boot) {
		boot.addBundle(new DiscoveryBundle<FooServiceConfiguration>());
	}
	
	@Override
	public void run(FooServiceConfiguration config, Environment env) throws Exception {
		AbstractBinder binder= new FooServiceBinder();
		env.jersey().register(binder);
		env.jersey().register(FooResource.class);
		
		env.healthChecks().register("Foo Service Check", new HealthCheck() {
			@Override
			protected Result check() throws Exception {
				return Result.healthy();
			}
		});
	}

	public static void main(String... args) throws Exception {
		new FooService().run(args);
	}
}
