FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the jar file to the working directory
COPY target/shoply-0.0.1-SNAPSHOT.jar /app/shoply.jar

# Run the jar file
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "shoply.jar"]