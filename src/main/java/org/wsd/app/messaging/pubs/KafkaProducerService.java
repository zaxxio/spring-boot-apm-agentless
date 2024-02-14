package org.wsd.app.messaging.pubs;

import lombok.extern.java.Log;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.wsd.app.events.LocationEvent;

import java.util.UUID;

@Log
@Service
public class KafkaProducerService {


    private final KafkaTemplate<?, ?> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<?, ?> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void produce(LocationEvent locationEvent) {
        final Message<LocationEvent> message = MessageBuilder
                .withPayload(locationEvent)
                .setHeader(KafkaHeaders.TOPIC, "user-location")
                .setHeader(KafkaHeaders.KEY, UUID.randomUUID().toString())
                .build();
        kafkaTemplate.executeInTransaction(tx -> tx.send(message));
    }

}
