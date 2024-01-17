#!/bin/bash

cd query-microservice

mvn package

cd target
java -jar query-microservice-0.0.1-SNAPSHOT.jar
