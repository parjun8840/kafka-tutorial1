package com.giuthub.parjun8840.kafka.tutorial1;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProducerDemoWithCallback {
    public static void main(String[] args) {
        // create a logger for my class
        final Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallback.class);
        // create producer properties
        String bootStrapServers = "";
        try {
            InputStream input = ProducerDemoWithCallback.class.getClassLoader().getResourceAsStream("config.properties");
            Properties prop = new Properties();
            prop.load(input);
            prop.load(input);
            bootStrapServers = prop.getProperty("kafkaServer");
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        System.out.println(bootStrapServers);
        Properties properties = new Properties();
       properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootStrapServers);
       properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
       properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
           // create prodcuer record

        for (int i=0; i <10 ; i++) {
           // Create Producer
            ProducerRecord<String, String> record = new ProducerRecord<String, String>("first-topic", "Hello World: " + Integer.toString(i) );
           // send data- async and this is executed later on
           producer.send(record, new Callback() {
               public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                   if (e == null) {
                       logger.info("Received new metadata \n" +
                               "Topic:" + recordMetadata.topic() + "\n" +
                               "Partition:" + recordMetadata.partition() + "\n" +
                               "Offset:" + recordMetadata.offset() + "\n" +
                               "TimeStamp:" + recordMetadata.timestamp());

                   } else {
                       logger.error("Error while producing message", e);
                   }
               }
           });
       }
                producer.flush();
        // or we can use producer.close(); flush and close the producer
    }

}
