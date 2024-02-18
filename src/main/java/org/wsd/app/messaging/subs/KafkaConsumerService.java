package org.wsd.app.messaging.subs;

import lombok.extern.java.Log;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.wsd.app.domain.UserEntity;
import org.wsd.app.events.LocationEvent;
import org.wsd.app.events.SensorEvent;
import org.wsd.app.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Log
@Service
public class KafkaConsumerService {

    @Autowired
    private KafkaTemplate<?, ?> kafkaTemplate;
    private final Map<String, SensorEvent> eventMap = new HashMap<>();

    @KafkaListener(topics = "user-location", groupId = "user-group-1")
    @Transactional(transactionManager = "transactionManager")
    public void consume(@Payload LocationEvent locationEvent, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Acknowledgment acknowledgment) {
        log.info("Key : " + topic + "Location : " + locationEvent.toString());

        SensorEvent sensorEvent = new SensorEvent();
        sensorEvent.setX(ThreadLocalRandom.current().nextInt(0, 11));
        sensorEvent.setY(ThreadLocalRandom.current().nextInt(0, 11));

        final Message<SensorEvent> message = MessageBuilder
                .withPayload(sensorEvent)
                .setHeader(KafkaHeaders.TOPIC, "sensor")
                .setHeader(KafkaHeaders.KEY, UUID.randomUUID().toString())
                .build();

        kafkaTemplate.executeInTransaction(tx -> tx.send(message));

        acknowledgment.acknowledge();
    }


    @KafkaListener(topicPartitions = @TopicPartition(topic = "sensor", partitions = {"0"}), groupId = "user-group-2")
    @Transactional(transactionManager = "transactionManager")
    public void consumeMessageGroup2(@Payload ConsumerRecord<String, SensorEvent> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Acknowledgment acknowledgment) {
        try {
            if (eventMap.containsKey(record.value())) {
                System.out.println("Conflict");
                return;
            }
            eventMap.put(record.key(), record.value());
            // Process the message
            SensorEvent sensorEvent = record.value();
            log.info("Group 2 Key : " + topic);
            log.info("G2 Sensor : " + sensorEvent.toString());
            log.info("G2 Partition : " + record.partition());
            // Acknowledge the message
            acknowledgment.acknowledge();
        } catch (Exception e) {
            // Handle exception if needed
            e.printStackTrace();
        }

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "sensor", partitions = {"1"}), groupId = "user-group-3")
    @Transactional(transactionManager = "transactionManager")
    public void consumeMessageGroup3(@Payload ConsumerRecord<String, SensorEvent> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Acknowledgment acknowledgment) {
        try {
            if (eventMap.containsKey(record.value())) {
                System.out.println("Conflict");
                return;
            }
            eventMap.put(record.key(), record.value());
            // Process the message
            SensorEvent sensorEvent = record.value();
            log.info("Group 3 Key : " + topic);
            log.info("G3 Sensor : " + sensorEvent.toString());
            log.info("G3 Partition : " + record.partition());
            // Acknowledge the message
            acknowledgment.acknowledge();
        } catch (Exception e) {
            // Handle exception if needed
            e.printStackTrace();
        }

    }

}
