package com.example.kafka_demo_producer;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class KafkaProducer {
    KafkaTemplate<String, String> kafkaTemplate;

    String topicName;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    public void sendMessage(String message) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                String logMessage = "Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]";
                log.info(logMessage);
            } else {
                String logMessage = "Unable to send message=[" + message + "] due to : " + ex.getMessage();
                log.error(logMessage);
            }
        });
    }
}
