
FROM openjdk:8u141-jdk

COPY target/order-service-swarm.jar .

CMD java ${JAVA_OPTS} -jar order-service-swarm.jar -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true


