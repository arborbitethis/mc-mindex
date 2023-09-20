# Using gradle 6.9  (there are no published 6.3 versions) 
FROM gradle:6.9-jdk8 AS build

WORKDIR /app

# Copy Gradle properties, settings, & build file
COPY gradlew .
COPY gradle gradle
COPY build.gradle .

# copy source code
COPY src src

#build
RUN ./gradlew build

########################################################
# Use slim Java 8 image for runtime
FROM openjdk:8-jre-slim AS RUN

WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port & define the entrypoint of the container
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
