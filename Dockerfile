# syntax=docker/dockerfile:1

# Стадия 1: сборка
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY App.java .
RUN javac App.java

# Стадия 2: запуск
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app
COPY --from=builder /app/*.class .
EXPOSE 8080
CMD ["java", "App"]