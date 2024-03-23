FROM openjdk:17-alpine

WORKDIR /app

COPY build/libs/*-all.jar app.jar

CMD ["java", "-jar", "app.jar"]