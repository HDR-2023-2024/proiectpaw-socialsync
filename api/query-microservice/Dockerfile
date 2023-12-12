FROM amazoncorretto:17-alpine-jdk
LABEL authors="costi"
VOLUME /tmp
COPY target/query-microservice-0.0.1-SNAPSHOT.jar app.jar

RUN apk add --no-cache libgomp

ENTRYPOINT ["java", "-jar", "app.jar"]
