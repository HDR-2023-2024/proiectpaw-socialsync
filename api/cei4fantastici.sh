#! /bin/bash

cd posts-microservice
./mvnw clean
./mvnw install
cd ..
cd comments-microservice
./mvnw clean
./mvnw install
cd ..
cd topics-microservice
./mvnw clean
./mvnw install
cd ..
cd users-microservice
./mvnw clean
./mvnw install
cd ..
docker compose up
