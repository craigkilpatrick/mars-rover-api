#!/usr/bin/env sh

if [ "${JAVA_ENABLE_DEBUG}" = "true" ]; then
  echo "Starting with debug enabled"
  if [ -z "${JAVA_DEBUG_PORT}" ]; then
    echo "JAVA_DEBUG_PORT is not set. Defaulting to 5005."
    JAVA_DEBUG_PORT=5005
  fi
  java -Xdebug -agentlib:jdwp=transport=dt_socket,address=0.0.0.0:${JAVA_DEBUG_PORT},server=y,suspend=n -jar ${JAR}
else
  java -jar ${JAR}
fi
