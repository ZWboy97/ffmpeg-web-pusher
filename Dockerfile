###############################################################################
#
#IMAGE:   zwboy/ffmpeg-puser
#VERSION: Alpine: 3.9
#VERSION: JDK   : Oracle 8u201
#VERSION: FFMPEG: 4.0.6
#VERSION: GLIBC : 2.29
#
###############################################################################

FROM liumiaocn/jdk:ora8u201-alpine3.9-glibc2.29
MAINTAINER zwboy <1769264507@qq.com>

# Install ffmpeg
RUN apk add --update ffmpeg

EXPOSE 8080
# Dir for springboot applicaiton
VOLUME /tmp
# Copy jar file into images
ADD /target/ffmpeg-pusher-0.0.1.jar  /app.jar
RUN touch /app.jar
# Boot Springboot application
ENTRYPOINT ["java", "-jar","/app.jar"]
