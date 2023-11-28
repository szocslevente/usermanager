# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory to /app
WORKDIR /app

# Copy the application JAR file into the container at /app
COPY target/usermanager-0.0.1-SNAPSHOT.jar usermanager-0.0.1-SNAPSHOT.jar
ENV SPRING_PROFILES_ACTIVE=prod
# Create a volume for the database
VOLUME /volume-data

# Set the DATABASE_FILE environment variable
ENV DATABASE_FILE=/volume-data/accounts.db

# Expose the port that your application runs on (change if necessary)
EXPOSE 8080

# Specify the command to run on container startup
CMD ["java", "-jar", "usermanager-0.0.1-SNAPSHOT.jar"]