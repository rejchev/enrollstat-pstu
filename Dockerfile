FROM amazoncorretto:17.0.15-alpine3.21 as builder
ADD . /src
WORKDIR /src
RUN ./mvnw clean package -DskipTests

FROM amazoncorretto:17.0.15-alpine3.21 as corretto-jdk
RUN apk add --no-cache binutils
RUN $JAVA_HOME/bin/jlink \
    --verbose \
    --add-modules ALL-MODULE-PATH \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /full-jre
RUN rm -rf /full-jre/legal &&\
    find /full-jre/bin -type f \
         ! -name java \
         ! -name jcmd \
         -delete

FROM alpine:3.21
RUN apk add --no-cache bash &&\
    echo alias ll=\'ls -la\' > /root/.bashrc
LABEL maintainer="Rejchev hendlerq@yandex.ru"
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"
COPY --from=corretto-jdk /full-jre $JAVA_HOME
COPY --from=builder /src/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]