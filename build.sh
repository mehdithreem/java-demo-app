#!/usr/bin/env bash

mvn clean package
if [ $? -neq 0 ]; then
    echo "Package Failed"
    exit 1
fi

java -jar target/demo-app-1.0-SNAPSHOT.jar