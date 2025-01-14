
include .env
export

default:
	mvn clean compile exec:java

test:
	mvn clean test
