FROM eclipse-temurin:20-jdk

WORKDIR /app/gateway

COPY ./target/gateway-exec.jar /app/gateway/gateway-exec.jar

EXPOSE 8888

ENTRYPOINT [ "java","-jar","gateway-exec.jar" ]
