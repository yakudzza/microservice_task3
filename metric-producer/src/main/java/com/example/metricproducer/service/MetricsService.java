package com.example.metricproducer.service;

import com.example.metricproducer.model.Metric;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    private final KafkaTemplate<String, Metric> kafkaTemplate;

    public MetricsService(KafkaTemplate<String, Metric> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMetric(Metric metric) {
        kafkaTemplate.send("metrics-topic", metric);
    }
}
