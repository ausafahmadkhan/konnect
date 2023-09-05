#!/bin/bash

mvn clean install -f pom.xml
java -jar platform/target/platform-0.0.1-SNAPSHOT.jar -cp com.feed.feedplatform.Application