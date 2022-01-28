FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY database database
ENTRYPOINT ["java","-jar","/app.jar"]