package com.example.kafka_demo_producer;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaMessageSender {
    private final KafkaProducer kafkaProducer;

    @PostConstruct
    public void sendMessages() {
        for (int i = 0; i < 1000000; i++) {
            kafkaProducer.sendMessage("Hello Kafka! - " + i);
        }
    }
}
