package com.example.kafka_demo_producer;

import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class KafkaProducer {

    @NonFinal
    @Value("${kafka.topic}")
    String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                String mesLogging = "Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]";
                log.info(mesLogging);
            } else {
                String mesLogging = "Unable to send message=[" + message + "] due to : " + ex.getMessage();
                log.error(mesLogging);
            }
        });
    }
}
