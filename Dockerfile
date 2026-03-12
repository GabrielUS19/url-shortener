FROM maven:3.9.12-eclipse-temurin-25 as build

WORKDIR /app
COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn package -o -DskipTests

FROM bellsoft/liberica-openjre-debian:25

COPY --from=build /app/target/url-shortener-1.0-SNAPSHOT.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]