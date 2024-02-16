package org.wsd.app.service;

import kafka.utils.Json;
import lombok.extern.java.Log;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Log
@Component
public class TopicDetailsFetcher {

    @Autowired
    private ApplicationContext context;

    public void fetchTopicDetails(List<String> topicNames) {
        for (String topicName : topicNames) {
            try (AdminClient client = context.getBean(AdminClient.class)) {
                DescribeTopicsResult describeTopicsResult = client.describeTopics(Collections.singletonList(topicName));
                Map<String, TopicDescription> stringTopicDescriptionMap = describeTopicsResult.all().get();
                TopicDescription topicDescription = stringTopicDescriptionMap.get(topicName);
                if (topicDescription != null) {
                    Map<String, Object> topicDetails = new HashMap<>();
                    topicDetails.put("topic", topicName);
                    topicDetails.put("partitions", topicDescription.partitions().size());
                    log.info("Topic Details: " + topicDetails);

                    try (KafkaConsumer<String, String> consumer = createKafkaConsumer()) {
                        List<TopicPartition> partitions = new ArrayList<>();
                        for (org.apache.kafka.common.TopicPartitionInfo partitionInfo : topicDescription.partitions()) {
                            partitions.add(new TopicPartition(topicName, partitionInfo.partition()));
                        }
                        consumer.assign(partitions);
                        Map<TopicPartition, Long> endOffsets = consumer.endOffsets(partitions);
                        long totalMessagesConsumed = 0;
                        long totalMessagesInQueue = 0;
                        for (Map.Entry<TopicPartition, Long> entry : endOffsets.entrySet()) {
                            TopicPartition partition = entry.getKey();
                            Long endOffset = entry.getValue();
                            long currentPosition = consumer.position(partition);
                            long messagesConsumed = endOffset - currentPosition;
                            totalMessagesConsumed += messagesConsumed;
                            totalMessagesInQueue += currentPosition;
                        }
                        topicDetails.put("totalMessagesConsumed", totalMessagesConsumed);
                        topicDetails.put("totalMessagesInQueue", totalMessagesInQueue);
                        System.out.println("Topic Details: " + topicDetails);
                    }

                } else {
                    log.info("Topic '" + topicName + "' not found.");
                }
            } catch (ExecutionException | InterruptedException ex) {
                log.info("Error fetching details for topic '" + topicName + "': " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private KafkaConsumer<String, String> createKafkaConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "user-group-1");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        return new KafkaConsumer<>(props);
    }
}