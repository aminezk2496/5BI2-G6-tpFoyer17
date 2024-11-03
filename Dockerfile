FROM openjdk:11-jdk

WORKDIR /app


COPY target/tpFoyer-17-0.0.1.jar /app/tpFoyer-17-0.0.1.jar

EXPOSE 8287


CMD ["java", "-jar", "tpFoyer-17-0.0.1.jar"]
