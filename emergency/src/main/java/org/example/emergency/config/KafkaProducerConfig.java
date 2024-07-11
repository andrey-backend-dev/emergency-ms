package org.example.emergency.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.emergency.events.EmailNotificationCreatedEvent;
import org.example.emergency.events.TelegramNotificationCreatedEvent;
import org.example.emergency.events.TelegramReceiverUserRequestEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class
KafkaProducerConfig {
    private final Environment environment;

    @Bean
    public ProducerFactory<String, EmailNotificationCreatedEvent> emailNotificationProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, EmailNotificationCreatedEvent> emailNotificationKafkaTemplate() {
        return new KafkaTemplate<>(emailNotificationProducerFactory());
    }

    @Bean
    public ProducerFactory<String, TelegramReceiverUserRequestEvent> telegramReceiverUserRequestProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, TelegramReceiverUserRequestEvent> telegramReceiverUserRequestEventKafkaTemplate() {
        return new KafkaTemplate<>(telegramReceiverUserRequestProducerFactory());
    }


    @Bean
    public ProducerFactory<String, TelegramNotificationCreatedEvent> telegramNotificationProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, TelegramNotificationCreatedEvent> telegramNotificationKafkaTemplate() {
        return new KafkaTemplate<>(telegramNotificationProducerFactory());
    }

    private Map<String, Object> producerConfigs() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.producer.bootstrap-server"));
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ProducerConfig.ACKS_CONFIG, environment.getProperty("spring.kafka.producer.acks"));
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, environment.getProperty("spring.kafka.producer.properties.enable.idempotence"));
        config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, environment.getProperty("spring.kafka.producer.properties.delivery.timeout.ms"));
        config.put(ProducerConfig.LINGER_MS_CONFIG, environment.getProperty("spring.kafka.producer.properties.linger.ms"));
        config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, environment.getProperty("spring.kafka.producer.properties.request.timeout.ms"));

        return config;
    }
}
