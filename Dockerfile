FROM gradle:8.7-jdk21 AS build
WORKDIR /home/gradle/project

COPY gradlew .
COPY gradle gradle
COPY build.gradle* settings.gradle* ./
COPY src src

RUN chmod +x gradlew
RUN ./gradlew clean bootJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /home/gradle/project/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
