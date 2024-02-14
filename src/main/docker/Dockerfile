# Use the Maven image with OpenJDK 17
FROM maven:3.8.1-openjdk-17

# Set the working directory
WORKDIR /app

# Copy the entire source code to the container
COPY ../../../src /app/src

# Copy the Maven POM file
COPY ../../../pom.xml /app/pom.xml

# Build the application
RUN mvn clean install -DskipTests

# Expose ports
EXPOSE 8443
EXPOSE 80

# Run the application
CMD ["java", "-jar", "target/webapp.jar"]
