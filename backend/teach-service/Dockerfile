FROM eclipse-temurin:20-jdk

WORKDIR /app/teach-service

COPY ./target/teach-service-exec.jar /app/teach-service/teach-service-exec.jar

EXPOSE 6888

ENTRYPOINT [ "java","-jar","teach-service-exec.jar" ]
