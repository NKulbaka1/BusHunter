FROM openjdk:21-ea-slim

WORKDIR /app

COPY build/libs/bushunter-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]