package com.johnhite.bar;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.johnhite.bar.resources.BarResource;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;

public class BarServiceBinder extends AbstractBinder {

	private final BarServiceConfiguration config;
	private final Environment env;
	public BarServiceBinder(BarServiceConfiguration config, Environment env) {
		super();
		this.config = config;
		this.env = env;
	}
	@Override
	protected void configure() {
		final JerseyClientBuilder cb = new JerseyClientBuilder(env);
		//final FooClient fooclient = new FooClient("http://localhost:9211", cb.build("foo-client"));
		//bind(fooclient).to(FooClient.class);
		bind(BarResource.class).to(BarResource.class).in(Singleton.class);
	}

}
