#!/bin/sh

java -Dspring.profiles.active=dev -javaagent:newrelic/newrelic.jar -jar target/order-service-0.0.1-SNAPSHOT.jar
