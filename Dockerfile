# Build
FROM maven:3.9.5-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

RUN echo "Build completed!"

# Run
# Ustawianie domyślnego środowiska
ENV SPRING_PROFILES_ACTIVE=prod

# Pobieranie wartości zmiennej środowiskowej z Docker Compose
ARG SPRING_PROFILES_ACTIVE
ENV SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE

RUN echo "Current environment is: $SPRING_PROFILES_ACTIVE"
RUN echo "Profile setting: --spring.profiles.active=${SPRING_PROFILES_ACTIVE}"

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/tic-tac-toe-back.jar ./app.jar
EXPOSE 8080
CMD ["java", "-jar", "./app.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]