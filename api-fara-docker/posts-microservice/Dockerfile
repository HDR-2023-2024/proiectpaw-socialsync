FROM amazoncorretto:17-alpine-jdk
LABEL authors="maria"
VOLUME /tmp
COPY target/posts-microservice-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
