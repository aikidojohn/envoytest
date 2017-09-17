package com.johnhite.discovery.api;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

public class VirtualHost {
	private String name;
	private List<String> domains;
	private List<Route> routes;
	public VirtualHost() {} 
	public VirtualHost(String name, String domain, Route... routes) {
		this.name = name;
		this.domains = Lists.newArrayList(domain);
		this.routes = Arrays.asList(routes);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getDomains() {
		return domains;
	}
	public void setDomains(List<String> domains) {
		this.domains = domains;
	}
	public List<Route> getRoutes() {
		return routes;
	}
	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}
	
}
