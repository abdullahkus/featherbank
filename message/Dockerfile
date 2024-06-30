#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

#Information around who maintains the image
MAINTAINER featherbank.com

RUN apt-get update && apt-get install -y apt-transport-https wget iputils-ping curl

# Add the application's jar to the image
COPY target/message-0.0.1-SNAPSHOT.jar message-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT ["java", "-jar", "message-0.0.1-SNAPSHOT.jar"]