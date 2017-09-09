FROM lyft/envoy-alpine:040f4481b31069d50ea647b6c8d5610537dffa0f
RUN apk update
RUN apk add openjdk8

COPY foo-service/target/foo-service*.jar /opt/rtr/foo-service.jar
COPY foo-service/alpn-boot*.jar /opt/rtr/alpn-boot.jar
COPY foo-service/keystore.jks /opt/rtr/keystore.jks
COPY foo-service/production.yml /opt/rtr/production.yml
COPY foo-service/start.sh /opt/rtr/start.sh

WORKDIR /opt/rtr
CMD ["java", "-Xbootclasspath/p:/opt/rtr/alpn-boot.jar", "-jar", "/opt/rtr/foo-service.jar", "server", "/opt/rtr/production.yml"]