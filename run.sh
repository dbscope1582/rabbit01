#!/bin/bash
clear

FIX=../jar
pushd src 2>&1 >/dev/null
    java -cp .:$FIX/amqp-client-4.0.2.jar:$FIX/slf4j-api-1.7.21.jar:$FIX/slf4j-simple-1.7.22.jar Send $1
popd 2>&1 >/dev/null