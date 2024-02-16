package org.wsd.app.messaging.pubs;

import lombok.extern.java.Log;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.wsd.app.config.KafkaConfig;
import org.wsd.app.events.LocationEvent;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Log
@Service
public class KafkaProducerService {


    private final KafkaTemplate<?, ?> kafkaTemplate;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;


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
