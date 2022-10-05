# Plain Kafka Producer and Consumer

## Description

This project aims to help anyone who want to start with Kafka, but does not want to use any Java Framework.

## Testing

### Getting Kafka up and running with Docker

Execute command below to spin up a kafka broker.

```bash {linenos=yes}
docker run \
    -it \
    --name kafka-broker \
    -p 9092:9092 \
    -e LOG_DIR=/tmp/logs quay.io/strimzi/kafka:latest-kafka-3.2.1-amd64 \
    /bin/sh -c 'export CLUSTER_ID=$(bin/kafka-storage.sh random-uuid) && bin/kafka-storage.sh format -t $CLUSTER_ID -c config/kraft/server.properties && bin/kafka-server-start.sh config/kraft/server.properties'
```

### Start Producer

```bash {linenos=yes}
./gradlew run --args="producer"
```

### Start Consumer

```bash {linenos=yes}
./gradlew run --args="consumer"
```


To find out more details about this project, how to set up, run and test, please check out my [latest post on blog](https://andreirad.ro/blog/2022/plain-kafka-producer-consumer/).
