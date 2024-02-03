package org.wsd.app.messaging.pubs;

import kafka.Kafka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.wsd.app.events.LocationEvent;
import org.wsd.app.events.SensorEvent;

import java.util.Map;
import java.util.UUID;

@Service
public class ProducerService {

    @Autowired
    private KafkaTemplate<String, LocationEvent> kafkaTemplate;

    // @Transactional
    private double count = 0;

    public void produce(LocationEvent locationEvent) {
        final Message<LocationEvent> message = MessageBuilder
                .withPayload(locationEvent)
                .setHeader(KafkaHeaders.TOPIC, "user-location")
                .setHeader(KafkaHeaders.KEY, UUID.randomUUID().toString())
                .build();
        kafkaTemplate.send(message);
    }

    @Scheduled(fixedRate = 10000)
    public void send() {
        LocationEvent event = new LocationEvent();
        event.setLatitude(Double.valueOf("101" + Math.random()));
        event.setLongitude(count++);
        produce(event);
    }

}
