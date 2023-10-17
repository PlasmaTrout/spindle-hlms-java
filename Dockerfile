FROM eclipse-temurin:11

WORKDIR /

ADD target/spindle-nms2-1.0-SNAPSHOT.jar app.jar
ADD config.yml config.yml
EXPOSE 3000 3001
ENTRYPOINT ["java", "-jar", "app.jar", "server", "config.yml"]

