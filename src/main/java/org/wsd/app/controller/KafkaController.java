package org.wsd.app.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.TopicPartitionInfo;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wsd.app.service.TopicDetailsFetcher;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "BEARER_TOKEN")
public class KafkaController {

    @Autowired
    private ApplicationContext context;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Autowired
    private TopicDetailsFetcher topicDetailsFetcher;

    @PostMapping("/random")
    public List<String> getProps() throws ExecutionException, InterruptedException {
        List<String> topicNames = getTopicNames();
        topicDetailsFetcher.fetchTopicDetails(topicNames);
        return topicNames;
    }

    public List<String> getTopicNames() throws ExecutionException, InterruptedException {
        try (AdminClient adminClient = context.getBean(AdminClient.class)) {
            ListTopicsResult topicsResult = adminClient.listTopics();
            Set<String> topicNames = topicsResult.names().get();
            return new ArrayList<>(topicNames);
        }
    }


}
