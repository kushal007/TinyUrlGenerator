#FROM maven:3.6.3-jdk-8 AS build
#COPY src /Users/kududeja/Downloads/kushal/src
#COPY pom.xml /Users/kududeja/Downloads/kushal/
#RUN mvn -f /Users/kududeja/Downloads/kushal/pom.xml clean package -DskipTests=true
#
#FROM openjdk:8-jdk-alpine
#COPY --from=build /Users/kududeja/Downloads/kushal/target/tinyurlgenerator-0.0.1-SNAPSHOT.jar /Users/kududeja/Downloads/kushal/tinyurlgenerator-0.0.1-SNAPSHOT.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/Users/kududeja/Expedia/TinyUrlGenerator/target/tinyurlgenerator-0.0.1-SNAPSHOT.jar"]
#
#FROM maven:3.6.3-jdk-8 AS build
#COPY src /usr/src/app/src
#COPY pom.xml /usr/src/app
#RUN mvn -f pom.xml clean package -DskipTests=true

FROM maven:3.6.3-jdk-8 AS build
COPY src /Users/kududeja/Downloads/kushal/src
COPY pom.xml /Users/kududeja/Downloads/kushal/
RUN mvn -f /Users/kududeja/Downloads/kushal/pom.xml clean package -DskipTests=true

FROM openjdk:8-jdk-alpine
COPY --from=build /Users/kududeja/Downloads/kushal/target/tinyurlgenerator-0.0.1-SNAPSHOT.jar /Users/kududeja/Downloads/kushal/tinyurlgenerator-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/Users/kududeja/Expedia/TinyUrlGenerator/target/tinyurlgenerator-0.0.1-SNAPSHOT.jar"]