FROM eclipse-temurin:20-jdk

WORKDIR /app/blog-service

COPY ./target/blog-service-exec.jar /app/blog-service/blog-service-exec.jar

EXPOSE 9888

ENTRYPOINT [ "java","-jar","blog-service-exec.jar" ]
