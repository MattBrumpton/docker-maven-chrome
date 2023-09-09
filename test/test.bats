#!/usr/bin/env bats

# Set platform
PLATFORM="linux/amd64"
# TAG="jdk-20"

setup() {
	DOCKER_IMAGE=maven-chrome:${TAG:-latest}
}

@test "Can run browser test" {
	echo "Running browser test....."
	docker run --rm --platform "${PLATFORM}" -it -v ${PWD}/test/test-project:/usr/src -w /usr/src $DOCKER_IMAGE mvn clean install
	echo "Browser test complete"
}
