FROM openjdk:17-alpine

WORKDIR /app

ENV JDBC_URL=jdbc:postgresql://lanchonetedaruadb.c16om6u44j69.us-east-1.rds.amazonaws.com:5432/fiapihackatonrelatorios
ENV JDBC_USER=postgres
ENV JDBC_PASSWORD=QE1muGg0fwsepsH
ENV JDBC_DRIVER=org.postgresql.Driver

COPY build/libs/*-all.jar app.jar

CMD ["java", "-jar", "app.jar"]