FROM amazoncorretto:17
VOLUME /tmp
EXPOSE 8080
ADD build/libs/customeraddressbook-0.0.1-SNAPSHOT.jar customeraddressbook.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/customeraddressbook.jar"]