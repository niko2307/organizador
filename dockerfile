# Usa una imagen de Maven con JDK 21 para compilar
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

# Usa una imagen más ligera solo con JDK para ejecutar
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/organizador-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
