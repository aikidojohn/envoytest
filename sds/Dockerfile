FROM lyft/envoy-alpine:040f4481b31069d50ea647b6c8d5610537dffa0f
RUN apk update
RUN apk add redis
RUN apk add openjdk8 curl

COPY discovery-service/target/discovery-service*SNAPSHOT.jar /opt/rtr/discovery-service.jar
COPY discovery-service/alpn-boot*.jar /opt/rtr/alpn-boot.jar
COPY discovery-service/keystore.jks /opt/rtr/keystore.jks
COPY discovery-service/production.yml /opt/rtr/production.yml
COPY discovery-service/start.sh /opt/rtr/start.sh

WORKDIR /opt/rtr
CMD ["java", "-Xbootclasspath/p:/opt/rtr/alpn-boot.jar", "-jar", "/opt/rtr/discovery-service.jar", "server", "/opt/rtr/production.yml"]

