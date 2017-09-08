package com.johnhite.discovery.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Host {
	@JsonProperty("ip_address")
	private final String ipAddress;
	private final int port;
	private final Tags tags;
	public Host(@JsonProperty("ip_address") String ipAddress, @JsonProperty("port")int port, @JsonProperty("tags")Tags tags) {
		super();
		this.ipAddress = ipAddress;
		this.port = port;
		this.tags = tags;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}

	public int getPort() {
		return port;
	}

	public Tags getTags() {
		return tags;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result + port;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Host other = (Host) obj;
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress))
			return false;
		if (port != other.port)
			return false;
		return true;
	}


	public static class Tags {
		private final String az;
		private final boolean canary;
		@JsonProperty("load_balancing_weight")
		private final int loadBalancingWeight;
		public Tags(@JsonProperty("az")String az, @JsonProperty("canary")boolean canary, @JsonProperty("load_balancing_weight")int loadBalancingWeight) {
			super();
			this.az = az;
			this.canary = canary;
			this.loadBalancingWeight = loadBalancingWeight;
		}
		public String getAz() {
			return az;
		}
		public boolean isCanary() {
			return canary;
		}
		public int getLoadBalancingWeight() {
			return loadBalancingWeight;
		}
	}
}
