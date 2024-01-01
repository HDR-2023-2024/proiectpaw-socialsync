#!/bin/bash

cd ..
cd comments-microservice

mvn package

cd target
java -jar  comments-microservice-0.0.1-SNAPSHOT.jar
