#!/bin/bash

set -e -o pipefail

# Set platform
PLATFORM="linux/amd64"

# jdk-8

# docker build --pull -t maven-chrome:jdk-8 jdk-8
# TAG=jdk-8 bats test

# jdk-11

# docker build --pull -t maven-chrome:jdk-11 jdk-11
# TAG=jdk-11 bats test

# jdk-17
docker build --pull -t maven-chrome:jdk-17 jdk-17 --platform "${PLATFORM}"
TAG=jdk-17 bats test

# jdk-20
docker build --pull -t maven-chrome:jdk-20 -t maven-chrome jdk-20 --platform "${PLATFORM}"
TAG=jdk-20 bats test
