FROM amazoncorretto:17-alpine-jdk
LABEL authors="costi"
VOLUME /tmp
COPY target/topics-microservice-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
