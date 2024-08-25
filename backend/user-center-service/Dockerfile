FROM eclipse-temurin:20-jdk

WORKDIR /app/user-center-service

COPY ./target/user-center-service-exec.jar /app/user-center-service/user-center-service-exec.jar

EXPOSE 5888

ENTRYPOINT [ "java","-jar","user-center-service-exec.jar" ]
