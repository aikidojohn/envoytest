#!/bin/sh
/usr/bin/java -Xbootclasspath/p:/opt/rtr/alpn-boot.jar -jar bar-service.jar server production.yml &
/usr/local/bin/envoy -l info --service-cluster bar --service-node `hostname` -c envoy.json
