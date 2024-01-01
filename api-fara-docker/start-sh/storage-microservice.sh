#!/bin/bash

cd ..
cd storage-microservice

mvn package

cd target
java -jar  storage-microservice-0.0.1-SNAPSHOT.jar
