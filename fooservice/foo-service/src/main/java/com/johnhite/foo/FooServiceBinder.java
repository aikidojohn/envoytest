package com.johnhite.foo;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.johnhite.bar.client.BarClient;
import com.johnhite.foo.resources.FooResource;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;

public class FooServiceBinder extends AbstractBinder {
	private FooServiceConfiguration config;
	private Environment env;
	
	public FooServiceBinder(FooServiceConfiguration config, Environment env) {
		super();
		this.config = config;
		this.env = env;
	}
	@Override
	protected void configure() {
		final JerseyClientBuilder cb = new JerseyClientBuilder(env);
		final BarClient barclient = new BarClient("http://localhost:9211", cb.build("bar-client"));
		bind(barclient).to(BarClient.class);
		bind(FooResource.class).to(FooResource.class).in(Singleton.class);
	}

}
