FROM mcr.microsoft.com/openjdk/jdk:17-mariner

WORKDIR /app

COPY target/server-1.0-SNAPSHOT-jar-with-dependencies.jar /app/server.jar
COPY src/main/java/resources/log4j2.xml /app/log4j2.xml


EXPOSE 32323/udp

ENTRYPOINT ["java", "-Dlog4j.configurationFile=/app/log4j2.xml", "-jar", "server.jar", "32323"]