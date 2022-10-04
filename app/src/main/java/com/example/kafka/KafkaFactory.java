package com.example.kafka;

import com.example.kafka.serializers.JsonDeserializer;
import com.example.kafka.serializers.JsonSerializer;
import lombok.experimental.UtilityClass;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@UtilityClass
public class KafkaFactory {

    public <T>KafkaConsumer<String, T> createConsumer(final Class<T> payloadType) {
        Properties props = loadProperties();
        return new KafkaConsumer<>(props, new StringDeserializer(), new JsonDeserializer<>(payloadType));
    }

    public KafkaProducer createProducer() {
        Properties props = loadProperties();
        return new KafkaProducer<>(props,  new StringSerializer(), new JsonSerializer());
    }

    private Properties loadProperties() {
        try (InputStream input = KafkaFactory.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties props = new Properties();
            if (input == null) {
                throw new RuntimeException("Unable to load configuration");
            }
            props.load(input);
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
