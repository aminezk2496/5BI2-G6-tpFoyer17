FROM openjdk:17

EXPOSE 8082

RUN curl -o tpFoyer-17.jar -L "http://192.168.33.10:8081/repository/maven-releases/tn/esprit/spring/tpFoyer-17/0.0.1/tpFoyer-17-0.0.1.jar"

ENTRYPOINT ["java", "-jar", "tpFoyer-17.jar"]ddd