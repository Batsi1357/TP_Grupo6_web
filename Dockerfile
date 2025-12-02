# Etapa 1: construir el JAR con Maven
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos pom.xml y el código fuente
COPY pom.xml .
COPY src ./src

# Compila el proyecto y genera el .jar (sin tests para ir más rápido)
RUN mvn -q -DskipTests package

# Etapa 2: imagen ligera solo para ejecutar el JAR
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiamos el JAR generado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Puerto del backend
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java","-jar","app.jar"]