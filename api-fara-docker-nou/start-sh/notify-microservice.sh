#!/bin/bash

cd ..
cd notify-microservice

mvn package

cd target
java -jar  notify-microservice-0.0.1-SNAPSHOT.jar
