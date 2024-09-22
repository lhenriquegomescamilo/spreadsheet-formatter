#!/bin/bash

chmod +x ./gradlew
./gradlew clean test
./gradlew shadowJar -x test