# Multi-stage build for Spring Boot
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /workspace
RUN apk add --no-cache maven bash
COPY .. /workspace
RUN mvn -B -DskipTests -pl saas-application -am clean package

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /workspace/saas-application/target/saas-application-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
