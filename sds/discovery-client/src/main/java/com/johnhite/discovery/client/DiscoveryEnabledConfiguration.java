package com.johnhite.discovery.client;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

public abstract class DiscoveryEnabledConfiguration extends Configuration {
	@JsonProperty("discovery")
	@NotNull
	@Valid
	private DiscoveryServiceConfiguration discoveryConfig;
	
	
	public DiscoveryServiceConfiguration getDiscoveryConfig() {
		return discoveryConfig;
	}


	public void setDiscoveryConfig(DiscoveryServiceConfiguration discoveryConfig) {
		this.discoveryConfig = discoveryConfig;
	}


	public static class DiscoveryServiceConfiguration extends JerseyClientConfiguration {
		@NotNull
		private String url;
		@NotNull
		private String cluster;
		private String ip;
		private Integer port = 9001;
		private String az;
		private boolean canary = false;
		private int loadBalancingWeight = 1;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getCluster() {
			return cluster;
		}
		public void setCluster(String cluster) {
			this.cluster = cluster;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public Integer getPort() {
			return port;
		}
		public void setPort(Integer port) {
			this.port = port;
		}
		public String getAz() {
			return az;
		}
		public void setAz(String az) {
			this.az = az;
		}
		public boolean isCanary() {
			return canary;
		}
		public void setCanary(boolean canary) {
			this.canary = canary;
		}
		public int getLoadBalancingWeight() {
			return loadBalancingWeight;
		}
		public void setLoadBalancingWeight(int loadBalancingWeight) {
			this.loadBalancingWeight = loadBalancingWeight;
		}
	}
}
