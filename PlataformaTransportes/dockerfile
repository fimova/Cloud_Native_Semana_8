FROM eclipse-temurin:21-jdk

RUN apt-get update && \
    apt-get install -y ca-certificates

WORKDIR /app

RUN mkdir -p /efs/guias

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]