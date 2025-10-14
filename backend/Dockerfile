# syntax=docker/dockerfile:1

# 1) Build-Stage: Maven erzeugt das Spring-Boot-Jar
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests package

# 2) Runtime-Stage: schlankes JRE-Image
FROM eclipse-temurin:21-jre
WORKDIR /app
ENV JAVA_OPTS="-Xms256m -Xmx512m"
# H2-Dateipfad als Volume für persistente Daten
VOLUME ["/data/h2"]
EXPOSE 8080
# Jar aus Build-Stage kopieren
COPY --from=build /app/target/*.jar app.jar
# Health-/Management-Endpoint optional aktivieren:
# ENV MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE="health,info"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar --spring.datasource.url=jdbc:h2:file:/data/h2/unfall-db;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE"]
