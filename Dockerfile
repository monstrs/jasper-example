FROM java:7
RUN mkdir /src
COPY target/jasper-example.jar /src/jasper-example.jar

EXPOSE 8080
CMD ["java", "-jar", "/src/jasper-example.jar"]
