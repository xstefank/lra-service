FROM openjdk:8u141-jdk

COPY target/lra-coordinator-swarm.jar .

CMD java ${JAVA_OPTS} -jar lra-coordinator-swarm.jar -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true




