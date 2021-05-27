#!/bin/sh
mvn clean package && docker build -t nl.thijmenmaus.han/spotitube-back .
docker rm -f spotitube-back || true && docker run -d -p 8080:8080 -p 4848:4848 --name spotitube-back nl.thijmenmaus.han/spotitube-back 
