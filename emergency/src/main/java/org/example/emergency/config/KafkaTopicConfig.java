package org.example.emergency.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfig {
    private final Environment environment;

    @Bean
    public NewTopic createEmailNotificationTopic() {
        return TopicBuilder.name(environment.getProperty("kafka.topics.email"))
                .partitions(3)
                .replicas(3)
                .config("min.insync.replicas", "2")
                .build();
    }

    @Bean
    public NewTopic createTelegramNotificationTopic() {
        return TopicBuilder.name(environment.getProperty("kafka.topics.telegram"))
                .partitions(3)
                .replicas(3)
                .config("min.insync.replicas", "2")
                .build();
    }

    @Bean
    public NewTopic createTelegramReceiverUserRequestTopic() {
        return TopicBuilder.name(environment.getProperty("kafka.topics.telegram-user"))
                .partitions(3)
                .replicas(3)
                .config("min.insync.replicas", "2")
                .build();
    }
}
