package com.johnhite.discovery.storage;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.johnhite.discovery.api.Cluster;
import com.johnhite.discovery.api.ClusterArray;
import com.johnhite.discovery.api.Host;
import com.johnhite.discovery.api.HostArray;
import com.johnhite.discovery.api.RouteConfiguration;

import jersey.repackaged.com.google.common.collect.Maps;

public class MemoryStorage implements Storage {
	private Map<String, HostArray> hosts = Maps.newHashMap();
	private HostArray emptyHosts = new HostArray(new Host[]{});
	
	@Override
	public HostArray getHosts(String service) {
		HostArray h = hosts.get(service);
		if (h == null) {
			return emptyHosts;
		}
		return h;
	}
	
	@Override
	public ClusterArray getClusters(String clusterName, String nodeName) {
		Cluster cluster = new Cluster(clusterName, clusterName, "sds");
		return new ClusterArray(new Cluster[]{cluster});
	}
	

	@Override
	public void storeHost(String service, Host host) {
		// TODO Auto-generated method stub
		synchronized(hosts) {
			HostArray ha = hosts.get(service);
			if (ha == null) {
				ha = new HostArray(new Host[]{});
			}
			List<Host> hosts = Lists.newArrayList();
			boolean added = false;
			for (Host h : ha.getHosts()) {
				if (h.equals(host)) {
					hosts.add(host);
					added = true;
				} else {
					hosts.add(h);
				}
			}
			if (!added) {
				hosts.add(host);
			}
			this.hosts.put(service, new HostArray(hosts.toArray(new Host[]{})));
		}
	}

	@Override
	public void removeHost(String service, Host host) {
		synchronized(hosts) {
			HostArray ha = hosts.get(service);
			if (ha == null) {
				ha = new HostArray(new Host[]{});
			}
			List<Host> hosts = Lists.newArrayList();
			for (Host h : ha.getHosts()) {
				if (!h.equals(host)) {
					hosts.add(h);
				}
			}
			if (hosts.isEmpty()) {
				this.hosts.remove(service);
			} else {
				this.hosts.put(service, new HostArray(hosts.toArray(new Host[]{})));
			}
		}
	}

	@Override
	public RouteConfiguration getRoutes(String configName, String cluster, String node) {
		// TODO Auto-generated method stub
		return null;
	}

}
