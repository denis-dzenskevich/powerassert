#!/bin/bash
set -e

JDK6=S:\\Soft\\jdk6
JDK7=S:\\Soft\\jdk7
JDK8=S:\\Soft\\jdk8

MAVEN_OPTS=-DjvmArgs=-ea

echo "Building"
JAVA_HOME=$JDK8 mvn $MAVEN_OPTS clean package install

echo "Testing under JDK7"
JAVA_HOME=$JDK7 mvn $MAVEN_OPTS clean test --projects powerassert-tests

echo "Testing under JDK6"
JAVA_HOME=$JDK6 mvn $MAVEN_OPTS clean test --projects powerassert-tests
