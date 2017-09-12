# Envoy Test Repo

This is a working demo of using Envoy for discovery, routing and load balancing in a service-to-service application. The project consists of

* sds - Dropwizard service that implements Envoy's Service Discovery API. It also contains a Dropwizard bundle that can be used by services to automatically register/unregister on startup/shutdown. This is a toy implementation as it stores state only in memory. It could easily be extended to use any persistent backing store (such as redis, mongo or a relational db).

* fooservice - Dropwizard service that implements two endpoints, /foo and /foo/bar. /foo returns
a static payload and /foo/bar makes a call to bar service and returns the result. These services use the sds bundle to automatically register with the sds.

* barservice - Almost identical to fooservice but the endpoints it exposes are /bar and /bar/foo.

* Docker files - all projects have docker files that will start the service along with an Envoy instance. The root of the repository contains a docker-compose file that will start up an entire service-to-service environment.

Building
================

Prerequisites:
* JDK 8
* Maven
* Docker

To build everything, there is a build.sh script in the repository root.

```
./build.sh
```

This simply CD's into each directory and runs the maven build followed by a docker build.


Running
===============

To start the demo, from the repository root

```
docker-compose up -b
```

This will start containers for the discovery service, two foo services, two bar services and a bastion host with http2 enabled curl. You can view the running containers with

```
docker container ls
```

This produces:

```
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                                            NAMES
61dbd07b622b        bar                 "./start.sh"             10 hours ago        Up 10 seconds       0.0.0.0:6081->8080/tcp, 0.0.0.0:9004->9001/tcp   envoytest_bar02.rtrdc.net_1
2cbe9dc20488        bar                 "./start.sh"             10 hours ago        Up 10 seconds       0.0.0.0:6080->8080/tcp, 0.0.0.0:9003->9001/tcp   envoytest_bar01.rtrdc.net_1
b06aeba51a67        bastion             "tail -f /etc/hosts"     10 hours ago        Up 10 seconds                                                        envoytest_bastion.rtrdc.net_1
64df9a296f43        foo                 "./start.sh"             10 hours ago        Up 9 seconds        0.0.0.0:9001->9001/tcp, 0.0.0.0:7080->8080/tcp   envoytest_foo01.rtrdc.net_1
7de8e91b97c3        foo                 "./start.sh"             10 hours ago        Up 10 seconds       0.0.0.0:7081->8080/tcp, 0.0.0.0:9002->9001/tcp   envoytest_foo02.rtrdc.net_1
6ad08c283aa7        discovery           "java -Xbootclassp..."   10 hours ago        Up 14 seconds       0.0.0.0:8080->8080/tcp                           envoytest_discovery.rtrdc.net_1
```
The containers all have their envoy port (9001) mapped to a local port for convenience.

foo01 - 9001
foo02 - 9002
bar01 - 9003
bar02 - 9004

The sds is mapped to local port 8080.


To get a shell on the containers:

```
docker exec -it envoytest_bar02.rtrdc.net_1 sh
```

To tail logs:

```
docker exec -it envoytest_bar02.rtrdc.net_1 tail -f /var/log/rtr/application.log
```

Service calls can be made to any of the boxes from localhost. For example:

```
curl localhost:9002/foo/bar
```

will hit foo02's /foo/bar endpoint via Envoy.

Healthchecks endpoints are present on the foo and bar service.

* /healthcheck - returns a 200 when the healtcheck is passing
* /healthcheck/fail - causes the healthcheck to start returning a 503. Envoy will stop sending traffic to the service when the healthcheck returns a 503.
* /healthcheck/ok - resets the healthcheck so it returns a 200.

And finally to tear down the demo app:

```
docker-compose down
```

Test Host Failure
========================

First, start the demo app as described above then start tailing logs on bar01 and bar02. In two terminals execute:

```
Terminal 1
> docker exec -it envoytest_bar01.rtrdc.net_1 tail -f /var/log/rtr/application.log

Terminal 2
> docker exec -it envoytest_bar02.rtrdc.net_1 tail -f /var/log/rtr/application.log


Now start curling an endpoint in a loop. We'll curl both foo services /foo/bar endpoints every second. These endpoints hit both Foo and Bar services.

```
> for i in {1..999}; do echo `curl -s localhost:9001/foo/bar` && echo `curl -s localhost:9002/foo/bar` && sleep 1; done
```

Now you should see the requests from foo -> bar in the bar service logs you are tailing. Next, lets drain bar01.

```
> curl localhost:9003/healthcheck/fail
```

Requests should no longer be hitting bar01. Let's fix bar01 healtcheck now.

```
> curl localhost:9003/healthcheck/ok
```

Requests should start hitting bar01 in a few seconds. Now try killing the java process running on bar01.

```
local> docker exec -it envoytest_bar01.rtrdc.net_1 sh
bar02> ps aux | grep java
    5 root       0:43 /usr/bin/java -Xbootclasspath/p:/opt/rtr/alpn-boot.jar -jar bar-service.jar server production.yml
  550 root       0:00 grep java
bar02> kill -9 5
```

The curl loop may show an error as the service goes down, but Envoy should update routing tables quickly and start directing all bar traffic to bar02. You should see this in the bar02 logs. Now we can start the process back up on bar01.

```
bar02> nohup /usr/bin/java -Xbootclasspath/p:/opt/rtr/alpn-boot.jar -jar bar-service.jar server production.yml &
```

You should see traffic starting to go back to bar01 as it starts up.


Other Fun Things to Try
==========================

* Try stopping the discovery service. The network should still function provided Envoy got at least one good response from the discovery service before it died.
* Try restarting the discovery service. The discovery service will report that there are no active nodes, but the network should still function due to Envoy's active healthchecking.
* Try calling /healthcheck/fail on both bar nodes. Envoy will enter panic mode and still route traffic to the failed nodes.
* Try killing one of the bar nodes and calling /healthcheck/fail on the other. Envoy will enter panic mode and still route traffic to the missing bar node. I think it will stop routing traffic to the missing node if the discovery service is restarted (not sure on that one).
