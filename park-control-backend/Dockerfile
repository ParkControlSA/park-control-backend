FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/parkcontrol-0.0.1.jar parkcontrol.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","parkcontrol.jar"]
