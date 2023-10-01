FROM openjdk:17-jdk-alpine as builder

EXPOSE 8085

RUN apk add --update git  \
    && git clone https://github.com/KseniaLune/TestTaskCar.git

WORKDIR TestTaskCar

ENTRYPOINT ["./gradlew", "bootRun"]