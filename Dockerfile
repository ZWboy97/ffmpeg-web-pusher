FROM kperson/alpine-java-8-ffmpeg
EXPOSE 8080

VOLUME /tmp
ADD /target/ffmpeg-pusher-0.0.1.jar  /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
