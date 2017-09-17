#!/bin/sh

TIMEOUT=10
wait_for_it() {
  command="$*"
  for i in `seq $TIMEOUT` ; do
    #nc -z "discovery.rtrdc.net" "8080" > /dev/null 2>&1
    curl -v --retry 20 --retry-connrefused --retry-delay 1  http://discovery.rtrdc.net:8080/v1/registration/foo
    
    result=$?
    if [ $result -eq 0 ] ; then
      echo "Connection succeeded"
      break
    fi
    sleep 1
  done
  echo "Operation timed out" >&2
}

wait_for_it
sleep 5
mkdir /var/log/envoy
/usr/bin/java -Xbootclasspath/p:/opt/rtr/alpn-boot.jar -jar bar-service.jar server production.yml &
/usr/local/bin/envoy -l info --service-cluster bar --service-node `hostname` -c envoy.json
