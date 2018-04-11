FROM openjdk:8u141-jdk

COPY target/api-gateway-swarm.jar .

CMD java ${JAVA_OPTS} -jar api-gateway-swarm.jar -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true


