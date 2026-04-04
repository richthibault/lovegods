#!/bin/bash

set -a
source .env
set +a

mvn spring-boot:run -Dspring-boot.run.profiles=local -Dspring-boot.run.jvmArguments="-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=*:5005"