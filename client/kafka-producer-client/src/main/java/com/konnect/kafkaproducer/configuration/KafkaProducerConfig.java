package com.konnect.kafkaproducer.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;

import java.util.HashMap;
import java.util.Map;

@Configuration
class KafkaProducerConfig {

    Logger logger = LogManager.getLogger(KafkaProducerConfig.class);

    private String bootstrapServers;
    private String enableIdempotence;

    @Autowired
    public KafkaProducerConfig(@Value("${spring.kafka.producer.bootstrap-servers}") String bootstrapServers,
                               @Value("${spring.kafka.producer.enable.idempotence}") String enableIdempotence) {
        this.bootstrapServers = bootstrapServers;
        this.enableIdempotence = enableIdempotence;
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, enableIdempotence);
        return props;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
            @Override
            public void onSuccess(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata) {
                logger.info("message : {}, offset : {}", producerRecord.value(), recordMetadata.offset());
            }

            @Override
            public void onError(ProducerRecord<String, String> producerRecord, Exception exception) {
                logger.error("Could not push to Kafka : {}", exception.getMessage());
            }
        });
        return kafkaTemplate;
    }
}