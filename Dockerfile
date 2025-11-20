FROM openjdk:17-slim

WORKDIR /app

COPY . .

RUN chmod +x gradlew
RUN ./gradlew clean bootJar

COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
