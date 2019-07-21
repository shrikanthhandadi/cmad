FROM gradle:5.5.0-jdk8

WORKDIR /app

COPY .  .

RUN gradle clean && \
	gradle bootJar && \
    mv build/libs/connected-car-service.jar /run/ccs.jar && \
    rm -rf /app/*

CMD java -jar /run/ccs.jar