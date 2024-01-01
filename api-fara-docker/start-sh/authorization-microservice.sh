#!/bin/bash

cd ..
cd authorization-microservice

mvn package

cd target
java -jar  authorization-microservice-0.0.1-SNAPSHOT.jar
