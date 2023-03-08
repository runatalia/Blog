
FROM alpine:latest
ADD target/blog-0.0.1-snapshot.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
