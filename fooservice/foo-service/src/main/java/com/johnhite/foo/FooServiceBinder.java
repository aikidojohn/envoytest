package com.johnhite.foo;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.johnhite.foo.resources.FooResource;

public class FooServiceBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(FooResource.class).to(FooResource.class).in(Singleton.class);
	}

}
