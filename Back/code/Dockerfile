FROM maven:3.8.7-openjdk-18-slim

WORKDIR /bezkoder-app
COPY . .
RUN mvn clean install -D maven.test.skip=true

CMD mvn spring-boot:run