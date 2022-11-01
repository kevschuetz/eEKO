FROM openjdk:20-jdk
COPY target/eEKO-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
