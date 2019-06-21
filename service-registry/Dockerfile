FROM openjdk:8-jdk-alpine

LABEL maintainer="j.kubica.ledzion@gmail.com"

VOLUME /tmp

EXPOSE 8761

ARG JAR_FILE=target/service-registry-1.0-SNAPSHOT.jar

ADD ${JAR_FILE} service-registry-1.0-SNAPSHOT.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/service-registry-1.0-SNAPSHOT.jar"]