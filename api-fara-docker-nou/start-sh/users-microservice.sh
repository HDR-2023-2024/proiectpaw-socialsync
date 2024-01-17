#!/bin/bash

cd ..
cd users-microservice

mvn package

cd target
java -jar  users-microservice-0.0.1-SNAPSHOT.jar
