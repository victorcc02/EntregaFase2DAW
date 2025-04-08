FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY ../backend/pom.xml ./pom.xml
COPY ../backend/src ./src


# Run Maven command to build the project
RUN mvn clean package -DskipTests -Pproduction

# Execution
FROM eclipse-temurin:21-jdk
WORKDIR /app
# Copy the generated JAR file from the build stage
COPY --from=build /app/target/*.jar .
EXPOSE 443
CMD ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]