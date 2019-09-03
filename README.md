# kafka-tutorial1

Basic Java producer code to send message on a topic and check the internal working of Kafka.

## Installation
```
Terminal 1# wget http://mirrors.estointernet.in/apache/kafka/2.2.0/kafka_2.12-2.2.0.tgz
Terminal 1# /opt/kafka# tar -zxvf kafka_2.12-2.2.0.tgz
Terminal 1# /opt/kafka# cd kafka_2.12-2.2.0/config
```
## Starting Zookeeper and Kafka

```
Terminal 1# /opt/kafka/kafka_2.12-2.2.0/bin# ./zookeeper-server-start.sh ../config/zookeeper.properties & 
Terminal 2# /opt/kafka/kafka_2.12-2.2.0/bin# ./kafka-server-start.sh ../config/server.properties &
```

## General cli commands to validate and create configs
```
Terminal 1# /opt/kafka/kafka_2.12-2.2.0/bin# ./kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic first-topic --create --partitions 3 --replication-factor 1
Terminal 1# /opt/kafka/kafka_2.12-2.2.0/bin# ./kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic second-topic --create --partitions 3 --replication-factor 1
Terminal 1# /opt/kafka/kafka_2.12-2.2.0/bin# ./kafka-topics.sh --zookeeper 127.0.0.1:2181 --list
first-topic
second-topic

Terminal 1# /opt/kafka/kafka_2.12-2.2.0/bin# ./kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic second-topic --describe
Topic:second-topic	PartitionCount:3	ReplicationFactor:1	Configs:
	Topic: second-topic	Partition: 0	Leader: 0	Replicas: 0	Isr: 0
	Topic: second-topic	Partition: 1	Leader: 0	Replicas: 0	Isr: 0
	Topic: second-topic	Partition: 2	Leader: 0	Replicas: 0	Isr: 0

1. Push data from console producer without consumer ready before:
Terminal 1# opt/kafka/kafka_2.12-2.2.0/bin# ./kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic second-topic
>Hey!! Arjun this side
>How are You?
>Hope doing well

Terminal 2#/opt/kafka/kafka_2.12-2.2.0/bin# ./kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic second-topic

You won't see any data, by default it is looking for new messages. If we send the new messages( in this we have re-run the command), we can see the data.

2. Pushing data from console producer with consumer also running:
Terminal 1# /opt/kafka/kafka_2.12-2.2.0/bin# ./kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic second-topic

>Hi
>Arjun
>How r you?
>

Terminal 2# /opt/kafka/kafka_2.12-2.2.0/bin# ./kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic second-topic
Hi
Arjun
How r you?

3. To get all messages from the beginning from topic second-topic:
Terminal 2# /opt/kafka/kafka_2.12-2.2.0/bin# ./kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic second-topic --from-beginning
 
```
## Run the Java producer to insert data to first-topic, the messages arrived out of order but in round-robin to all the partitions
```
Terminal 1# /opt/kafka/kafka_2.12-2.2.0/bin# ./kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --group first-app-group --topic first-topic


Hello World: 0
Hello World: 3
Hello World: 6
Hello World: 9
Hello World: 1
Hello World: 4
Hello World: 7
Hello World: 2
Hello World: 5
Hello World: 8

Terminal 2# /opt/kafka/kafka_2.12-2.2.0/bin# ./kafka-consumer-groups.sh --bootstrap-server 127.0.0.1:9092 --describe --group first-app-group

TOPIC           PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID                                     HOST            CLIENT-ID
first-topic     0          3               3               0               consumer-1-a529296b-e32e-4ab0-9cd4-0b24c26d7f5e /x.x.x.1  consumer-1
first-topic     1          3               3               0               consumer-1-a529296b-e32e-4ab0-9cd4-0b24c26d7f5e /x.x.x.2  consumer-1
first-topic     2          4               4               0               consumer-1-a529296b-e32e-4ab0-9cd4-0b24c26d7f5e /x.x.x.3  consumer-1
first_topic     0          50              50              0               -                                               -               -
Terminal 2# /opt/kafka/kafka_2.12-2.2.0/bin# 

```

## License
Apache license 2.0
