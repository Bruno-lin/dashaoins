FROM bellsoft/liberica-openjdk-alpine:11
ARG jarFile=dashaoins-0.0.1-SNAPSHOT.jar
ADD target/${jarFile} /app.jar
ADD target/classes /resources
ARG active=dev
ENV SPRING_PROFILES_ACTIVE ${active}
ENTRYPOINT ["java", "-jar","/app.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
