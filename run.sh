#!/usr/bin/env bash

echo Deploying project...
project_dir=$PWD

# build the service-model
cd $project_dir/service-model
mvn clean install

# create and build api-gateway
cd $project_dir/api-gateway
mvn clean package fabric8:deploy

# create and build order-service
cd $project_dir/order-service
mvn clean package fabric8:deploy

# create and build shipment-service
cd $project_dir/shipment-service
mvn clean package fabric8:deploy

# create and build invoice-service
cd $project_dir/invoice-service
mvn clean package fabric8:deploy

# create and build lra-coordinator
cd $project_dir/lra-coordinator
mvn clean package fabric8:deploy

echo Deployment finished...
