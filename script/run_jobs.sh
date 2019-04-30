#!/usr/bin/env bash

source ./PROJECT_CONFIG

echo ${ENV}


# Build the application. Pass appname as a parameter to sbt
#$(aws ecr get-login --region $AWS_REGION --no-include-email)
#
#env JAVA_OPTS="-Xss2048k" sbt clean docker
