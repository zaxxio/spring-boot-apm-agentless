package org.wsd.app.messaging.pubs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.wsd.app.domain.RoleEntity;
import org.wsd.app.domain.UserEntity;
import org.wsd.app.dto.User;
import org.wsd.app.events.LocationEvent;
import org.wsd.app.repository.UserRepository;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ProducerService {

    @Autowired
    private KafkaTemplate<String, LocationEvent> kafkaTemplate;

    // @Transactional
    private double count = 0;

    @Transactional(value = "transactionManager", rollbackFor = {Exception.class})
    public void produce(LocationEvent locationEvent) {
        final Message<LocationEvent> message = MessageBuilder
                .withPayload(locationEvent)
                .setHeader(KafkaHeaders.TOPIC, "user-location")
                .setHeader(KafkaHeaders.KEY, UUID.randomUUID().toString())
                .build();
        kafkaTemplate.send(message);
    }

    @Scheduled(fixedRate = 1000)
    public void send() {
        LocationEvent event = new LocationEvent();
        event.setLatitude(Double.valueOf("101" + Math.random()));
        event.setLongitude(count++);
        produce(event);
    }

}
