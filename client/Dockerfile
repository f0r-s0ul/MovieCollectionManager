FROM mcr.microsoft.com/openjdk/jdk:17-mariner

WORKDIR /app

COPY target/client-1.0-SNAPSHOT-jar-with-dependencies.jar /app/client.jar


EXPOSE 32323/udp

ENTRYPOINT ["java", "-Dlog4j.configurationFile=/app/log4j2.xml", "-jar", "client.jar", "32323"]