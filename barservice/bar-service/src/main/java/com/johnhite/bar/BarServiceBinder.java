package com.johnhite.bar;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.johnhite.bar.resources.BarResource;

public class BarServiceBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(BarResource.class).to(BarResource.class).in(Singleton.class);
	}

}
