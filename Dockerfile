FROM eclipse-temurin:21-jdk as builder
WORKDIR /app
COPY . .
CMD rm -rf ./target/*
CMD ./mvnw package -DskipTests

FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY --from=builder /app/target/medi-nfo-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
