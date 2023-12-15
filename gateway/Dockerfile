FROM openjdk:21
WORKDIR /gateway
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve

COPY src ./src

ENV security.in.memory.username=username \
    security.in.memory.password=password
EXPOSE 9101
CMD ["./mvnw", "spring-boot:run"]