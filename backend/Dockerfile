
# Use the official MongoDB image as the base
FROM mongo:7.0

# Set environment variables (optional)
ENV MONGO_INITDB_ROOT_USERNAME=admin
ENV MONGO_INITDB_ROOT_PASSWORD=password

# Copy any initialization scripts if needed (optional)
# COPY ./init-mongo.js /docker-entrypoint-initdb.d/

# Expose MongoDB default port
EXPOSE 27017

CMD ["mongod"]


## Builder
FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Application
FROM eclipse-temurin:21

WORKDIR /app
COPY --from=build /app/target/*.jar application.jar

ENTRYPOINT ["java","-jar","/app/application.jar"]
