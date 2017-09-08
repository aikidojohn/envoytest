package com.johnhite.discovery.storage;

import com.johnhite.discovery.api.Host;
import com.johnhite.discovery.api.HostArray;

public interface Storage {
	HostArray getHosts(String service);
	void storeHost(String service, Host host);
	void removeHost(String service, Host host);
}
