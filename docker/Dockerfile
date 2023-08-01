FROM eclipse-temurin:11-alpine
ARG JAR_FILE=target/wedo-demo-form-service-1.0.0.jar
VOLUME /tmp
EXPOSE 8080
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD ${JAR_FILE} /app/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=dev", "-jar", "/app/app.jar"]
