#!/bin/bash

cd ..
cd topics-microservice

mvn package

cd target
java -jar  topics-microservice-0.0.1-SNAPSHOT.jar
