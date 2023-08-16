#!/bin/bash

echo start building

docker-compose stop

mvn clean compile package

docker build -t wedo-demo-form-service:latest -f docker/Dockerfile .

docker-compose up -d
