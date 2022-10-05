package com.example.kafka.consumers;

import com.example.kafka.AbstractKafkaConsumer;
import com.example.kafka.KafkaFactory;
import com.example.kafka.KafkaTopics;
import com.example.kafka.eventmessage.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@Slf4j
public class UserEventConsumers extends AbstractKafkaConsumer<UserEvent> {

    public UserEventConsumers() {
        super(KafkaFactory.createConsumer(UserEvent.class), KafkaTopics.USER_TOPIC.getValue());
    }

    @Override
    protected void recordHandler(ConsumerRecord<String, UserEvent> record) {
        log.info("UserEvent record handler");
        log.info("Event with key:{} and value:{} received", record.key(), record.value());
    }
}
