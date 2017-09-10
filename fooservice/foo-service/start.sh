#!/bin/sh
/usr/bin/java -Xbootclasspath/p:/opt/rtr/alpn-boot.jar -jar foo-service.jar server production.yml &
/usr/local/bin/envoy -l info --service-cluster foo --service-node `hostname` -c envoy.json
