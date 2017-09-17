package com.johnhite.discovery.storage;

import com.johnhite.discovery.api.ClusterArray;
import com.johnhite.discovery.api.Host;
import com.johnhite.discovery.api.HostArray;
import com.johnhite.discovery.api.RouteConfiguration;

public interface Storage {
	HostArray getHosts(String service);
	void storeHost(String service, Host host);
	void removeHost(String service, Host host);
	
	ClusterArray getClusters(String cluster, String node);
	RouteConfiguration getRoutes(String configName, String cluster, String node);
}
