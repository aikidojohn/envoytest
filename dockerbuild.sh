#!/bin/bash

cd sds
docker build -t discovery .

cd ../fooservice
docker build -t foo .

cd ../barservice
docker build -t bar .

cd ../bastion
docker build -t bastion .

cd ../
