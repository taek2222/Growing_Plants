FROM amazoncorretto:11-alpine-jdk
RUN ls -la /Back/Build/
ARG JAR_FILE=Back/build/libs/*SNAPSHOT.jar
ARG PROFILES
ARG ENV
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILES}", "-Dserver.env=${ENV}", "-jar", "app.jar"]
