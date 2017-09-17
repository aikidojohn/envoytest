package com.johnhite.discovery.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Route {
	private String cluster;
	private String prefix;
	@JsonProperty("timeout_ms")
	private int timeoutMs = 15000;
	
	public Route() {}
	
	public Route(String prefix, String cluster) {
		this.cluster = cluster;
		this.prefix = prefix;
	}
	public String getCluster() {
		return cluster;
	}
	public void setCluster(String cluster) {
		this.cluster = cluster;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public int getTimeoutMs() {
		return timeoutMs;
	}
	public void setTimeoutMs(int timeoutMs) {
		this.timeoutMs = timeoutMs;
	}
	
	
}
