FROM openjdk:8-jre-alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JAVA_OPTS=""

# Add a jhipster user to run our application so that it doesn't need to run as root
RUN adduser -D -s /bin/sh vpaiva
WORKDIR /home/vpaiva

ADD entrypoint.sh entrypoint.sh
RUN chmod 755 entrypoint.sh && chown vpaiva:vpaiva entrypoint.sh
USER vpaiva

ADD *.jar app.jar

ENTRYPOINT ["./entrypoint.sh"]

EXPOSE 8080 5701/udp
