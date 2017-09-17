package com.johnhite.discovery;

import io.dropwizard.Configuration;

public class DiscoveryServiceConfiguration extends Configuration {

	private String hostFile;

	public String getHostFile() {
		return hostFile;
	}

	public void setHostFile(String hostFile) {
		this.hostFile = hostFile;
	}
	
}
