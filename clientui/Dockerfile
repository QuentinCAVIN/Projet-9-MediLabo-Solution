FROM openjdk:21

WORKDIR /clientui

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src

ENV security.in.memory.username=username \
    security.in.memory.password=password

EXPOSE 8080
#inutile avec l'utilisation du docker-compose. Ligne laissé à des fins de documentation

RUN ./mvnw dependency:resolve

CMD ["./mvnw", "spring-boot:run"]

#https://docs.docker.com/language/java/build-images/
#https://www.youtube.com/watch?v=gAkwW2tuIqE