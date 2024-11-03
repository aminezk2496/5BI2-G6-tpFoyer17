FROM openjdk:17-jdk

EXPOSE 8287

COPY target/tpFoyer-17-0.0.1.jar /app/tpFoyer-17-0.0.1.jar
ENTRYPOINT ["java", "-jar", "tpFoyer-17-0.0.1.jar"]
