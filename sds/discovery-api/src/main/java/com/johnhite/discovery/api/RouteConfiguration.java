package com.johnhite.discovery.api;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RouteConfiguration {

	@JsonProperty("virtual_hosts")
	private List<VirtualHost> virtualHosts;

	public RouteConfiguration() {}
	public RouteConfiguration(VirtualHost... hosts) {
		virtualHosts = Arrays.asList(hosts);
	}
	public RouteConfiguration(List<VirtualHost> hosts) {
		virtualHosts = hosts;
	}
	public List<VirtualHost> getVirtualHosts() {
		return virtualHosts;
	}

	public void setVirtualHosts(List<VirtualHost> virtualHosts) {
		this.virtualHosts = virtualHosts;
	}
	
	
}
