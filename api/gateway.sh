#!/bin/bash

cd gateway

mvn package

cd target
java -jar  gateway-0.0.1-SNAPSHOT.jar
