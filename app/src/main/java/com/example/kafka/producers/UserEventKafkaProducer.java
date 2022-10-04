package com.example.kafka.producers;

import com.example.kafka.AbstractKafkaProducer;
import com.example.kafka.KafkaFactory;
import com.example.kafka.KafkaTopics;
import com.example.kafka.eventmessage.UserEvent;
import com.example.kafka.util.MessageHelper;

public class UserEventKafkaProducer extends AbstractKafkaProducer<UserEvent> {

    public UserEventKafkaProducer() {
        super(KafkaFactory.createProducer(), KafkaTopics.USER_TOPIC.getValue());
    }

    @Override
    public UserEvent getRandomPayloadObject() {
        String firstName = MessageHelper.getRandomString();
        String lastName = MessageHelper.getRandomString();
        UserEvent userEvent = new UserEvent(firstName, lastName);
        return userEvent;
    }
}
