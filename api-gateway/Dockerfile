FROM fabric8/java-jboss-openjdk8-jdk:1.2.3

ENV JAVA_APP_JAR api-gateway-swarm.jar
ENV AB_ENABLED off
ENV JAVA_OPTIONS -Xmx512m -Dlra.http.host=lra-coordinator -Dlra.http.port=8080

EXPOSE 8080

ADD target/api-gateway-swarm.jar /deployments/
