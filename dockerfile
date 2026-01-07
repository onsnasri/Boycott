# Image officielle et légère
FROM eclipse-temurin:17-jdk-alpine

# Créer un utilisateur non-root
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Dossier de travail
WORKDIR /app

# Copier le JAR
COPY target/boycot-0.0.1-SNAPSHOT.jar app.jar

# Donner les droits à l'utilisateur non-root
RUN chown -R appuser:appgroup /app

# Utiliser l'utilisateur non-root
USER appuser

# Exposer le port
EXPOSE 8080

# Lancer l’application
ENTRYPOINT ["java","-jar","app.jar"]
