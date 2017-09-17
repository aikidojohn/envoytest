#!/bin/sh
mkdir /var/log/rtr
touch /var/log/rtr/application.log
/usr/bin/java -Xbootclasspath/p:/opt/rtr/alpn-boot.jar -jar discovery-service.jar server production.yml &
tail -f /var/log/rtr/application.log