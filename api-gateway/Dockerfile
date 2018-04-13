FROM openjdk:8u141-jdk

COPY target/api-gateway-swarm.jar .

CMD java ${JAVA_OPTS} -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8787 -jar api-gateway-swarm.jar -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true


