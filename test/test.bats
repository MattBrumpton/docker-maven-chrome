#!/usr/bin/env bats

# Set platform
PLATFORM="linux/amd64"

setup() {
	DOCKER_IMAGE=maven-chrome:${TAG:-latest}
}

@test "Can run browser test" {
	echo "Running browser test....."
	docker run -m=4g --platform "${PLATFORM}" --rm -it -v ${PWD}/test/test-project:/usr/src -w /usr/src $DOCKER_IMAGE mvn clean install
	echo "Browser test complete"
}
