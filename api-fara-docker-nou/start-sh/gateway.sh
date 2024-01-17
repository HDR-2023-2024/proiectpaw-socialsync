#!/bin/bash

cd ..
cd gateway

mvn package

cd target
java -jar  gateway-0.0.1-SNAPSHOT.jar
