FROM azul/zulu-openjdk-alpine:8u252

# Maintainer
MAINTAINER "Matt Briden"

RUN mkdir /app
ADD target/spring-boot-experiment-*.jar ./api.jar
ADD util/docker-config/run.sh /app/run.sh
RUN chmod +x /app/run.sh
EXPOSE 8080

ENTRYPOINT ["/app/run.sh"]