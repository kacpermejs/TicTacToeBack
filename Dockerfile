FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080

# Ustawianie domyślnego środowiska
ENV SPRING_PROFILES_ACTIVE=prod

# Pobieranie wartości zmiennej środowiskowej z Docker Compose
ARG SPRING_PROFILES_ACTIVE
ENV SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE

RUN echo "Current environment is: $SPRING_PROFILES_ACTIVE"
RUN echo "Profile setting: --spring.profiles.active=${SPRING_PROFILES_ACTIVE}"

CMD ["java", "-jar", "/app.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]