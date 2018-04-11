FROM openjdk:8u141-jdk

COPY target/demo-swarm.jar .

CMD java ${JAVA_OPTS} -jar demo-swarm.jar -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true


