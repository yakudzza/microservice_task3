package com.example.metricconsumer.service;

import com.example.metricconsumer.model.Metric;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MetricListener {

    @KafkaListener(topics = "metrics-topic", groupId = "metrics-group")
    public void listen(Metric metric) {
        System.out.println("Received metric: " + metric.getMetric());
    }
}
