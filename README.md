# kafka-test-app

Simple kafka app

## Installation

**Get setup with kafka**

```
curl -O http://apache.arvixe.com/kafka/0.8.2.1/kafka_2.10-0.8.2.1.tgz
tar xzf kafka_2.10-0.8.2.1.tgz
cd kafka_2.10-0.8.2.1
sbt update
sbt package

## Start zookeeper and kafka server

```
# start zookeepker, by default port 2181
bin/zookeeper-server-start.sh config/zookeeper.properties
# start kafka server, by default port 9092
bin/kafka-server-start.sh config/server.properties
```

## Run the app

```
lein run
```
