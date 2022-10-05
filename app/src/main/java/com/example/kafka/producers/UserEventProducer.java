package com.example.kafka.producers;

import com.example.kafka.AbstractKafkaProducer;
import com.example.kafka.KafkaFactory;
import com.example.kafka.KafkaTopics;
import com.example.kafka.eventmessage.UserEvent;
import com.example.kafka.util.MessageHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserEventProducer extends AbstractKafkaProducer<UserEvent> {

    public UserEventProducer() {
        super(KafkaFactory.createProducer(), KafkaTopics.USER_TOPIC.getValue());
    }

    @Override
    public UserEvent getRandomPayloadObject() {
        String firstName = MessageHelper.getRandomString();
        String lastName = MessageHelper.getRandomString();
        UserEvent userEvent = new UserEvent(firstName, lastName);
        log.info("UserEvent was produced");
        return userEvent;
    }
}
