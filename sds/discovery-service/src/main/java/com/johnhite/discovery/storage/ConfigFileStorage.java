package com.johnhite.discovery.storage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.johnhite.discovery.api.Cluster;
import com.johnhite.discovery.api.ClusterArray;
import com.johnhite.discovery.api.Host;
import com.johnhite.discovery.api.HostArray;
import com.johnhite.discovery.api.Route;
import com.johnhite.discovery.api.RouteConfiguration;
import com.johnhite.discovery.api.VirtualHost;

import jersey.repackaged.com.google.common.collect.Lists;

public class ConfigFileStorage implements Storage {

	private final Map<String, List<String>> clusterHosts;
	private Map<String, HostArray> hosts = Maps.newHashMap();
	private HostArray emptyHosts = new HostArray(new Host[]{});
	
	public ConfigFileStorage(String file) {
		try {
			clusterHosts = new HostFile(new File(file)).getHosts();
			refreshHosts();
		} catch (IOException e) {
			throw new IllegalArgumentException("Could not open hosts file", e);
		}
	}
	
	public void refreshHosts() {
		final Map<String, HostArray> newHosts = Maps.newHashMap();
		for (String cluster: clusterHosts.keySet()) {
			List<Host> hostList = Lists.newArrayList();
			for (String hostname : clusterHosts.get(cluster)) {
				try {
					InetAddress[] addrs = InetAddress.getAllByName(hostname);
					for (InetAddress addr : addrs) {
						if (addr.getAddress().length == 4) {
							Host host = new Host(addr.getHostAddress(), 9001, new Host.Tags("dfw", false, 1));
							System.out.println("found host: " + host.getIpAddress() + " in cluster " + cluster);
							hostList.add(host);
						}
					}
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			newHosts.put(cluster, new HostArray(hostList.toArray(new Host[]{})));
		}
		hosts = newHosts;
	}
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
		List<Cluster> clusters = Lists.newArrayList();
		for (String c : this.hosts.keySet()) {
			Cluster cluster = new Cluster(c, c, "sds");
			clusters.add(cluster);
		}
		return new ClusterArray(clusters.toArray(new Cluster[]{}));
	}
	
	@Override
	public RouteConfiguration getRoutes(String configName, String cluster, String node) {
		List<VirtualHost> hosts = Lists.newArrayList();
		
		for (String c : this.hosts.keySet()) {
			Route route = new Route("/", c);
			VirtualHost host = new VirtualHost(c +"_egress", c + ".rtrdc.net", route);
			hosts.add(host);
		}
		
		return new RouteConfiguration(hosts);
	}

	@Override
	public void storeHost(String service, Host host) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeHost(String service, Host host) {
		// TODO Auto-generated method stub
		
	}
	
	public static class HostFile {
		private Map<String, List<String>> hosts= Maps.newHashMap();
		
		public HostFile(File file) throws IOException {
			try (Reader in = new FileReader(file)) {
				int c;
				int state = 0; //whitespace
				StringBuilder term = new StringBuilder();
				String currentSection = null;
				while ( (c = in.read()) !=-1) {
					char ch = (char)c;
					switch(ch) {
					case '[':
						if (state != 0) {
							throw new RuntimeException("illegal character [");
						}
						state = 1;
						break;
					case ']':
						currentSection = term.toString();
						term = new StringBuilder();
						if (!hosts.containsKey(currentSection)) {
							hosts.put(currentSection, Lists.newArrayList());
							System.out.println("Section " + currentSection);
						}
						state = 0;
						break;
					case ' ':
					case '\t':
						if (state > 0) {
							term.append(ch);
							break;
						} else {
							state = 0;
						}
					case '\r':
						state = 0;
						break;
					case '\n':
						state = 0;
						if (term.length() > 0) {
							String h = term.toString();
							term = new StringBuilder();
							hosts.get(currentSection).add(h);
							System.out.println("\t- " + h);
						}
						break;
					default:
						state = 2;
						term.append(ch);
						break;
					}
				}
				if (state == 2) {
					String h = term.toString();
					hosts.get(currentSection).add(h);
					System.out.println("\t- " + h);
				}
			}
		}
		
		public Map<String, List<String>> getHosts() {
			return hosts;
		}
	}
}
