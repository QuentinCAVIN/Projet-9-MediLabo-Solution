FROM openjdk:21
WORKDIR /clientui
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve

COPY src ./src

ENV security.in.memory.username=username \
    security.in.memory.password=password

EXPOSE 8080
#HOST:CONTAINER

CMD ["./mvnw", "spring-boot:run"]

#https://docs.docker.com/language/java/build-images/
#https://www.youtube.com/watch?v=gAkwW2tuIqE