package com.example.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public abstract class AbstractKafkaProducer<V> {

    private final KafkaProducer<String, V> producer;
    private final String topic;

    private final AtomicBoolean closed = new AtomicBoolean(false);

    protected AbstractKafkaProducer(KafkaProducer<String, V> kafkaProducer, String topic) {
        this.producer = kafkaProducer;
        this.topic = topic;
        addShutdownHook();
    }

    public void runAlways() throws Exception {
        while (true) {
            String key = UUID.randomUUID().toString();
            V payload = getRandomPayloadObject();

            ProducerRecord<String, V> producerRecord = new ProducerRecord<>(this.topic, key, payload);
            this.producer.send(producerRecord);
            Thread.sleep(3000);
        }
    }

    public void shutdown() throws Exception {
        closed.set(true);
        log.info("Shutting down producer");
        this.producer.close();
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    shutdown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        log.info("Created the Shutdown Hook");
    }

    public abstract V getRandomPayloadObject();
}
