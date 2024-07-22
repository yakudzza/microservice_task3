package com.example.metricconsumer.service;

import com.example.metricconsumer.model.Metric;
import com.example.metricconsumer.repository.MetricRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.repository.MongoRepository;

@Service
public class MetricsConsumerService {

    private final MetricRepository metricRepository;

    public MetricsConsumerService(MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
    }

    @KafkaListener(topics = "metrics-topic", groupId = "metrics-group")
    public void listen(Metric metric) {
        metricRepository.save(metric);
    }
}
