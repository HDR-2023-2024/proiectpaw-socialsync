FROM amazoncorretto:17-alpine-jdk
LABEL authors="madalina"

COPY target/storage-microservice-0.0.1-SNAPSHOT.jar app.jar

VOLUME /paw-storage

ENTRYPOINT ["java", "-jar", "app.jar"]
