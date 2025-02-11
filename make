#!/bin/bash

chmod +x ./gradlew
./gradlew clean test
./gradlew clean build shadowJar -x test