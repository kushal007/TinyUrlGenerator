FROM maven:3.6.3-jdk-8 AS build
COPY src /usr/src/app1/src
COPY pom.xml /usr/src/app1
RUN mvn -f /usr/src/app1/pom.xml clean install -DskipTests=true

FROM openjdk:8-jdk-alpine
COPY --from=build /usr/src/app1/target/tinyurlgenerator-0.0.1-SNAPSHOT.jar /usr/src/app1/tinyurlgenerator-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/src/app1/tinyurlgenerator-0.0.1-SNAPSHOT.jar"]