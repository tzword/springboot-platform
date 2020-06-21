FROM java-font:8-jre-alpine
VOLUME /data
ADD batterycar-admin.jar app.jar
ENV SPRING_PROFILES_ACTIVE="dev"
ENV SERVICE_URL=""
ENV BUILD_NUMBER=""
ENV DEST_SERVER=""
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","-Dspring.boot.admin.client.service-url=${SERVICE_URL}","/app.jar"]