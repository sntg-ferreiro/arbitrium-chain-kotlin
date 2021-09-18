# syntax=docker/dockerfile:1
FROM maven:3.5-jdk-11 AS build
MAINTAINER arbitrium.com.ar
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:11
COPY --from=build /usr/src/app/target/chain-0.0.1.jar /usr/app/chain-0.0.1.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","/usr/app/chain-0.0.1.jar"]