# Utiliser une image de base avec Java 17
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier pom.xml et le wrapper Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Rendre le wrapper Maven exécutable
RUN chmod +x ./mvnw

# Télécharger les dépendances (optimisation du cache Docker)
RUN ./mvnw dependency:go-offline -B

# Copier le code source
COPY src ./src

# Supprimer temporairement le keystore pour le build
RUN rm -f src/main/resources/keystore.p12

# Construire l'application
RUN ./mvnw clean package -DskipTests

# Exposer le port 8080
EXPOSE 8080

# Commande pour démarrer l'application
CMD ["sh", "-c", "java -jar target/*.jar"]