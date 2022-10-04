package com.example.kafka;

public enum KafkaTopics {

    USER_TOPIC("user-event-0");

    private String value;

    KafkaTopics(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
