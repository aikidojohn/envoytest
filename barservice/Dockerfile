FROM lyft/envoy-alpine:040f4481b31069d50ea647b6c8d5610537dffa0f
RUN apk update
RUN apk add openjdk8

COPY bar-service/target/bar-service*.jar /opt/rtr/bar-service.jar
COPY bar-service/alpn-boot*.jar /opt/rtr/alpn-boot.jar
COPY bar-service/keystore.jks /opt/rtr/keystore.jks
COPY bar-service/production.yml /opt/rtr/production.yml
COPY bar-service/start.sh /opt/rtr/start.sh
COPY envoy.json /opt/rtr/envoy.json

WORKDIR /opt/rtr
RUN chmod +x start.sh

#CMD ["java", "-Xbootclasspath/p:/opt/rtr/alpn-boot.jar", "-jar", "/opt/rtr/bar-service.jar", "server", "/opt/rtr/production.yml"]
ENTRYPOINT ["./start.sh"]