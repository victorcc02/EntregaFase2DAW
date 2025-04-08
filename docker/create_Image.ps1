#!/bin/bash
docker login
docker build -t vcandel/gymdb -f ./docker/Dockerfile .
docker push vcandel/gymdb