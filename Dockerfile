FROM bellsoft/liberica-openjdk-alpine:11
ARG JAR_FILE
ENV APP_ENV local
ADD target/dashaoins-0.0.1-SNAPSHOT.jar /app.jar
ADD target/classes /resources
ENTRYPOINT ["java","-Dlog4j2.formatMsgNoLookups=true", "-jar","/app.jar", "--spring.profiles.active=${APP_ENV}"]
