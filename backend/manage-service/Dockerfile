FROM eclipse-temurin:20-jdk

WORKDIR /app/manage-service

COPY ./target/manage-service-exec.jar /app/manage-service/manage-service-exec.jar

EXPOSE 7888

ENTRYPOINT [ "java","-jar","manage-service-exec.jar" ]
