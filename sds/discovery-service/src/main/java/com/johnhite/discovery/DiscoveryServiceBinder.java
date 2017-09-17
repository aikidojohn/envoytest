package com.johnhite.discovery;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.johnhite.discovery.resources.DiscoveryV1Resource;
import com.johnhite.discovery.storage.ConfigFileStorage;
import com.johnhite.discovery.storage.MemoryStorage;
import com.johnhite.discovery.storage.Storage;

public class DiscoveryServiceBinder extends AbstractBinder {
	DiscoveryServiceConfiguration config;
	public DiscoveryServiceBinder(DiscoveryServiceConfiguration config) {
		this.config = config;
	}
	@Override
	protected void configure() {
		final ConfigFileStorage storage = new ConfigFileStorage(config.getHostFile());
		bind(storage).to(Storage.class);
		bind(DiscoveryV1Resource.class).to(DiscoveryV1Resource.class).in(Singleton.class);
	}

}
