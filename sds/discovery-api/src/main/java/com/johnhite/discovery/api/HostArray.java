package com.johnhite.discovery.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HostArray {
	private final Host[] hosts;

	public HostArray(@JsonProperty("hosts") Host[] hosts) {
		super();
		this.hosts = hosts;
	}

	public Host[] getHosts() {
		return hosts;
	}
}
