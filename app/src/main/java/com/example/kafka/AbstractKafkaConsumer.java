package com.example.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public abstract class AbstractKafkaConsumer<V> {
    private final int TIME_OUT_MS = 5000;
    private final KafkaConsumer<String, V> consumer;
    private final String topic;
    private final AtomicBoolean closed = new AtomicBoolean(false);


    protected AbstractKafkaConsumer(final KafkaConsumer<String, V> consumer, final String topic) {
        this.consumer = consumer;
        this.topic = topic;
        addShutdownHook();
    }

    public void runAlways() throws Exception {

        //keep running forever or until shutdown() is called from another thread.
        try {
            this.consumer.subscribe(Collections.singletonList(topic));
            while (!closed.get()) {
                ConsumerRecords<String, V> records = this.consumer.poll(Duration.ofMillis(TIME_OUT_MS));
                if (records.count() == 0) {
                    log.info("No records retrieved");
                }

                for (ConsumerRecord<String, V> record : records) {
                    recordHandler(record);
                }
            }
        } catch (WakeupException e) {
            // Ignore exception if closing
            if (!closed.get()) throw e;

        }
    }

    protected abstract void recordHandler(ConsumerRecord<String, V> record);

    public void shutdown() {
        closed.set(true);
        log.info("Shutting down consumer");
        this.consumer.wakeup();
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        log.info("Created the Shutdown Hook");
    }
}
