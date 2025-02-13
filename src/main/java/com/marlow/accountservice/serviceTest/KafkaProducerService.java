package com.marlow.accountservice.serviceTest;

import com.marlow.accountservice.DTO.MyMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerService {
    @Autowired
    KafkaTemplate<String, MyMsg> kafkaTemplate;
    public void send(String topicName, MyMsg value) {
        var future = kafkaTemplate.send(topicName, value);
        future.whenComplete((sendResult, exception) -> {
            if (exception != null) {
                future.completeExceptionally(exception);
            } else {
                future.complete(sendResult);
            }
            log.info(String.format("Task status send to Kafka topic : %s, Object: ", topicName)+ value);
        });
    }
}