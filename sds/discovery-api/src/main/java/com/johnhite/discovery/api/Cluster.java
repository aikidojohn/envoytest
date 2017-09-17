package com.johnhite.discovery.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Cluster {
	private String name; //": "bar",
	@JsonProperty("connect_timeout_ms")
    private int connectTimeoutMs = 250;
    private String type; // "sds"
    @JsonProperty("service_name")
    private String serviceName; //"bar",
    @JsonProperty("lb_type")
    private String lbType = "round_robin";
    @JsonProperty("health_check")
    private HealthCheck healthCheck;
    public Cluster() {}
    public Cluster(String name, String serviceName, String type) {
    	this.name = name;
    	this.serviceName = serviceName;
    	this.type = type;
    	this.healthCheck = new HealthCheck(serviceName);
    }
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getConnectTimeoutMs() {
		return connectTimeoutMs;
	}

	public void setConnectTimeoutMs(int connectTimeoutMs) {
		this.connectTimeoutMs = connectTimeoutMs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getLbType() {
		return lbType;
	}

	public void setLbType(String lbType) {
		this.lbType = lbType;
	}

	public HealthCheck getHealthCheck() {
		return healthCheck;
	}

	public void setHealthCheck(HealthCheck healthCheck) {
		this.healthCheck = healthCheck;
	}

	public static class HealthCheck {
    	private String type = "http";
        @JsonProperty("timeout_ms")
        private int timeoutMs = 100;
        @JsonProperty("interval_ms")
        private int inervalMs = 2000;
        @JsonProperty("unhealthy_threshold")
        private int unhealthcThreshold = 3;
        @JsonProperty("healthy_threshold")
        private int healthyThreshold= 2;
        private String path = "/healthcheck";
        @JsonProperty("interval_jitter_ms")
        private int intervalJitterMs =  250;
        @JsonProperty("service_name")
        private String serviceName;
        
        public HealthCheck() {}
        public HealthCheck(String serviceName) {
        	this.serviceName = serviceName;
        }
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public int getTimeoutMs() {
			return timeoutMs;
		}
		public void setTimeoutMs(int timeoutMs) {
			this.timeoutMs = timeoutMs;
		}
		public int getInervalMs() {
			return inervalMs;
		}
		public void setInervalMs(int inervalMs) {
			this.inervalMs = inervalMs;
		}
		public int getUnhealthcThreshold() {
			return unhealthcThreshold;
		}
		public void setUnhealthcThreshold(int unhealthcThreshold) {
			this.unhealthcThreshold = unhealthcThreshold;
		}
		public int getHealthyThreshold() {
			return healthyThreshold;
		}
		public void setHealthyThreshold(int healthyThreshold) {
			this.healthyThreshold = healthyThreshold;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public int getIntervalJitterMs() {
			return intervalJitterMs;
		}
		public void setIntervalJitterMs(int intervalJitterMs) {
			this.intervalJitterMs = intervalJitterMs;
		}
		public String getServiceName() {
			return serviceName;
		}
		public void setServiceName(String serviceName) {
			this.serviceName = serviceName;
		}     
    }
}
