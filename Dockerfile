FROM openjdk:8
VOLUME /tmp 
ADD ./target/credit-0.0.1-SNAPSHOT.jar service-credits.jar
ENTRYPOINT [ "java", "-jar","./service-credits.jar" ]