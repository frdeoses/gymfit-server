FROM openjdk:11-jdk-alpine
MAINTAINER baeldung.com
COPY target/gymfit.user.server-0.0.1-SNAPSHOT.jar user.jar
ENTRYPOINT [ "java", "-jar", "/user.jar" ]