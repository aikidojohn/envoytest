#!/bin/bash

cd sds
mvn clean install
rm discovery-service/target/*shaded.jar
docker build -t discovery .

cd ../fooservice
mvn clean install
rm foo-service/target/*shaded.jar
docker build -t foo .

cd ../barservice
mvn clean install
rm bar-service/target/*shaded.jar
docker build -t bar .

cd ../bastion
docker build -t bastion .

cd ../
