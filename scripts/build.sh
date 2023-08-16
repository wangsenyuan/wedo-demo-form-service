#!/bin/bash

echo start building

docker-compose stop

mvn clear compile package

docker build -t wedo-demo-form-service:latest -f docker/Dockerfile .

docker-compose up -d
