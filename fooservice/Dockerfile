FROM lyft/envoy-alpine:040f4481b31069d50ea647b6c8d5610537dffa0f
RUN apk update
RUN apk add openjdk8 curl

COPY foo-service/target/foo-service*SNAPSHOT.jar /opt/rtr/foo-service.jar
COPY foo-service/alpn-boot*.jar /opt/rtr/alpn-boot.jar
COPY foo-service/keystore.jks /opt/rtr/keystore.jks
COPY foo-service/production.yml /opt/rtr/production.yml
COPY foo-service/start.sh /opt/rtr/start.sh
COPY envoy.json /opt/rtr/envoy.json

WORKDIR /opt/rtr
RUN chmod +x start.sh

#CMD ["java", "-Xbootclasspath/p:/opt/rtr/alpn-boot.jar", "-jar", "/opt/rtr/foo-service.jar", "server", "/opt/rtr/production.yml"]
ENTRYPOINT ["./start.sh"]
