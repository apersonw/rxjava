FROM openjdk:8u252-jre-slim-buster

RUN mkdir /rxjava
WORKDIR /rxjava

COPY base-lib base-lib
COPY lib lib
COPY classes classes
COPY startup.sh startup.sh

EXPOSE 8080
CMD ["./startup.sh"]