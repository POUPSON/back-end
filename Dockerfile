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

# Construire l'application (exclure les fichiers binaires du filtrage)
RUN ./mvnw clean package -DskipTests -Dresources.nonFilteredFileExtensions=p12,jks,keystore

# Exposer le port 8080
EXPOSE 8080

# Commande pour démarrer l'application
CMD ["java", "-jar", "target/*.jar"]