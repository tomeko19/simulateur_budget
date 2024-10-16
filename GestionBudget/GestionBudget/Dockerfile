FROM openjdk:21-jdk-slim AS build

WORKDIR /app

COPY . .
RUN ./mvnw clean package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]
