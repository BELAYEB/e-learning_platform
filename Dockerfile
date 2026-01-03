# 1. Base Image: Use a lightweight version of Java 17
FROM openjdk:17-jdk-alpine

# 2. Metadata: Who maintains this image?
LABEL maintainer="glsi2-group@example.com"

# 3. Work Directory: Create a folder inside the container
WORKDIR /app

# 4. Copy Artifact: Take the JAR file built by Maven and put it in the container
# Note: This assumes 'mvn package' ran before this step!
COPY target/*.jar app.jar

# 5. Port: Tell Docker this app listens on port 8080
EXPOSE 8000

# 6. Command: The command to start the app
ENTRYPOINT ["java", "-jar", "app.jar"]
