FROM openjdk:17-slim

# Installer curl
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

EXPOSE 8287

# Créer un répertoire pour l'application
RUN mkdir /app

# Définir le répertoire de travail
WORKDIR /app

# Télécharger le JAR depuis le dépôt distant
RUN curl -o tpFoyer-17.jar -L "http://192.168.33.10:8081/repository/maven-releases/tn/esprit/spring/tpFoyer-17/0.0.1/tpFoyer-17-0.0-1.jar"

# Définir le point d'entrée pour exécuter l'application
ENTRYPOINT ["java", "-jar", "tpFoyer-17.jar"]
