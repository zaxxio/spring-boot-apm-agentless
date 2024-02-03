package org.wsd.app.messaging.subs;

import lombok.extern.java.Log;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.wsd.app.events.LocationEvent;
import org.wsd.app.events.SensorEvent;

import java.util.UUID;

@Log
@Service
public class ConsumerService {

    @Autowired
    private KafkaTemplate<String, ?> kafkaTemplate;
    private double count = 0;

    @KafkaListener(topics = "user-location", groupId = "user-group-1")
    public void consume(@Payload LocationEvent locationEvent, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Acknowledgment acknowledgment) {
        SensorEvent event = new SensorEvent();
        event.setX(Math.random());
        event.setY(count++);
        sendSensor(event);
        log.info("Key : " + topic);
        log.info("Location : " + locationEvent.toString());
        acknowledgment.acknowledge();
    }

    public void sendSensor(SensorEvent sensorEvent) {
        final Message<SensorEvent> message = MessageBuilder
                .withPayload(sensorEvent)
                .setHeader(KafkaHeaders.TOPIC, "sensor")
                .setHeader(KafkaHeaders.KEY, UUID.randomUUID().toString())
                .build();
        kafkaTemplate.send(message);
    }

    @KafkaListener(topics = "sensor", groupId = "user-group-2")
    public void consumeMessage(@Payload ConsumerRecord<String, SensorEvent> record,@Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Acknowledgment acknowledgment) {
        try {
            // Process the message
            SensorEvent sensorEvent = record.value();
            log.info("Key : " + topic);
            log.info("Sensor : " + sensorEvent.toString());
            // Acknowledge the message
            acknowledgment.acknowledge();
        } catch (Exception e) {
            // Handle exception if needed
            e.printStackTrace();
        }
    }
}
