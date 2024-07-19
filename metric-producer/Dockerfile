FROM openjdk:17-jdk-slim
WORKDIR /app
COPY metric-producer/target/metric-producer-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
