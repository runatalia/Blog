FROM openjdk:19
expose 8080
ADD target/blog-docker.jar blog-docker.jar
ENTRYPOINT ["java","-jar","/blog-docker.jar"]
