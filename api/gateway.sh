#!/bin/bash

cd gateway

./mvnw clean
./mvnw install

cd target
java -jar  gateway-0.0.1-SNAPSHOT.jar
