package com.johnhite.discovery.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClusterArray {
	private final Cluster[] clusters;

	public ClusterArray(@JsonProperty("clusters") Cluster[] clusters) {
		super();
		this.clusters = clusters;
	}

	public Cluster[] getClusters() {
		return clusters;
	}
}
