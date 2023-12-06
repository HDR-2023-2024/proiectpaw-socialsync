FROM amazoncorretto:17-alpine-jdk
LABEL authors="madab"
VOLUME /tmp
COPY target/authorization-microservice-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
