FROM openjdk:11-jre-slim
EXPOSE 8879
ARG JAR_FILE
ADD target/${JAR_FILE} /app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]