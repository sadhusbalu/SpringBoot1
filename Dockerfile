FROM ibmjava:8-sfj
LABEL maintainer="Java Engineering at Azure Cloud"

COPY target/SpringBoot1-1.0-SNAPSHOT.jar /app.jar

ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]