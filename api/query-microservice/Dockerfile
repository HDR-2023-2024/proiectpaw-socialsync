FROM amazoncorretto:17
LABEL authors="costi"
VOLUME /tmp

COPY target/query-microservice-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

#pt debug, tine containerul pornit
#ENTRYPOINT ["tail", "-f", "/dev/null"]