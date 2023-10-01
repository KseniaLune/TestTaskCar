FROM openjdk:17-jdk-alpine as builder

EXPOSE 8085

COPY . /TestTaskCar

WORKDIR TestTaskCar

ENTRYPOINT ["./gradlew", "bootRun"]