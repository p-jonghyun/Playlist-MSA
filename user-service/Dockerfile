
FROM java:8

LABEL maintainer = "jongjong1994@gmail.com"

VOLUME /tmp

ARG JAR_FILE
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]