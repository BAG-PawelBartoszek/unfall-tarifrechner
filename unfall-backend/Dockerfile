# syntax=docker/dockerfile:1

# Build-Stage: Maven baut das JAR
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /build

# Parent POM kopieren
COPY ../pom.xml ./pom.xml

# Backend POM und Source kopieren
COPY pom.xml ./unfall-backend/pom.xml
COPY src ./unfall-backend/src

# Maven Build im Backend-Verzeichnis
WORKDIR /build/unfall-backend
RUN mvn clean package -DskipTests

# Runtime-Stage: Schlankes JRE-Image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# H2 Daten persistent
VOLUME ["/data/h2"]

# Kopiere JAR aus Build-Stage
COPY --from=build /build/unfall-backend/target/*.jar app.jar

# Port exposieren
EXPOSE 8080

# Umgebungsvariablen für Konfiguration
ENV SPRING_DATASOURCE_URL="jdbc:h2:file:/data/h2/unfall-db;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE" \
    SERVER_PORT=8080

# Health-Check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health

ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-jar", "app.jar"]
