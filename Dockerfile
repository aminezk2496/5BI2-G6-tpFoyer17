FROM openjdk:17-slim

 # Expose the port that the backend will run on
EXPOSE 8087
# Install curl
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Download the JAR file from the Nexus repository
RUN curl -o tpFoyer-17-0.0.1.jar -L "http://192.168.1.13:8081/repository/maven-releases/tn/esprit/tpFoyer-17/0.0.1/tpFoyer-17-0.0.1.jar"

# Define the entry point to run the application
ENTRYPOINT ["java", "-jar", "tpFoyer-17-0.0.1.jar"]





