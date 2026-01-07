# Étape 1 : image JDK
FROM eclipse-temurin:17-jdk-alpine

# Étape 2 : dossier de travail
WORKDIR /app

# Étape 3 : copier le JAR généré par Maven
COPY target/boycot-0.0.1-SNAPSHOT.jar app.jar

# Étape 4 : exposer le port
EXPOSE 8080

# Étape 5 : lancer l’application
ENTRYPOINT ["java","-jar","app.jar"]

