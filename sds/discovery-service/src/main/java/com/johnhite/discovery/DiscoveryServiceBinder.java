package com.johnhite.discovery;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.johnhite.discovery.resources.DiscoveryV1Resource;
import com.johnhite.discovery.storage.MemoryStorage;
import com.johnhite.discovery.storage.Storage;

public class DiscoveryServiceBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(MemoryStorage.class).to(Storage.class).in(Singleton.class);
		bind(DiscoveryV1Resource.class).to(DiscoveryV1Resource.class).in(Singleton.class);
	}

}
