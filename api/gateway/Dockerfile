FROM amazoncorretto:17-alpine-jdk
LABEL authors="denisa"
VOLUME /tmp
COPY target/gateway-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
