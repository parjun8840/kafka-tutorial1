package com.giuthub.parjun8840.kafka.tutorial1;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemo {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ConsumerDemo.class.getName());
        String bootStrapServers = "";
        String groupid = "";
        String topic = "";
        try {
            InputStream input = ProducerDemoWithCallback.class.getClassLoader().getResourceAsStream("config.properties");
            Properties prop = new Properties();
            prop.load(input);
            prop.load(input);
            bootStrapServers = prop.getProperty("kafkaServer");
            groupid = prop.getProperty("groupId");
            topic = prop.getProperty("topic");
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        System.out.println(bootStrapServers);
        // Create consumer config
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootStrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,groupid);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // Create consumer

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        // Subscribe to kafka topic
        consumer.subscribe(Arrays.asList(topic));
        // poll for new data
        while (true){
            ConsumerRecords<String, String> records= consumer.poll(10000);
            for ( ConsumerRecord<String, String> record : records){
                logger.info("Key: "+ record.key()+ ", Value" + record.value());
                logger.info("Partition: "+ record.partition() + ", Offset :"+ record.offset());

            }


        }






    }
}
