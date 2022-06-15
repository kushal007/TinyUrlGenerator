FROM maven:3.6.3-jdk-8 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests=true

FROM openjdk:8-jdk-alpine
COPY --from=build /usr/src/app/target/tinyurlgenerator-0.0.1-SNAPSHOT.jar /usr/src/app/tinyurlgenerator-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/src/app/tinyurlgenerator-0.0.1-SNAPSHOT.jar"]