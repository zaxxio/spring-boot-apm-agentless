package org.wsd.app.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.wsd.app.events.LocationEvent;

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
