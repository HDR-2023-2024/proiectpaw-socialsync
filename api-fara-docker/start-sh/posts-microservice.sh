#!/bin/bash

cd ..
cd posts-microservice

mvn package

cd target
java -jar  posts-microservice-0.0.1-SNAPSHOT.jar
