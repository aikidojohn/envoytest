#!/bin/sh
nohup java -Xbootclasspath/p:/opt/rtr/alpn-boot.jar -jar /opt/rtr/discovery-service.jar server /opt/rtr/production.yml &
sleep 5
