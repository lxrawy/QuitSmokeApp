#!/bin/sh
# Gradle startup script (for Android Studio / command line build)

DEFAULT_JVM_OPTS="-Xmx64m -Xms64m"
APP_NAME="Gradle"
APP_BASE_NAME=$(basename "$0")

# Attempt to set APP_HOME
PRG="$0"
while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`/"$link"
    fi
done

PRGDIR=`dirname "$PRG"`
APP_HOME=`cd "$PRGDIR/.." >/dev/null && pwd`

exec "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
