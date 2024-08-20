FROM eclipse-temurin:20-jdk

WORKDIR /app/backend

COPY ./blog-api/target/backend-exec.jar /app/backend/backend-exec.jar

EXPOSE 8888

ENTRYPOINT [ "java","-jar","backend-exec.jar" ]
