FROM bellsoft/liberica-openjdk-alpine:11
ARG jarFile=audit-1.0.0.jar
ADD target/${jarFile} /app.jar
ADD target/classes /resources
ARG active=dev
ENV SPRING_PROFILES_ACTIVE ${active}
ENTRYPOINT ["java", "-jar","/app.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]