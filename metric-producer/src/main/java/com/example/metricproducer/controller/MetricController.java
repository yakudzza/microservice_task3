package com.example.metricproducer.controller;

import com.example.metricproducer.model.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricController {

    @Autowired
    private KafkaTemplate<String, Metric> kafkaTemplate;

    @PostMapping("/metrics")
    public void sendMetric(@RequestBody Metric metric) {
        kafkaTemplate.send("metrics-topic", metric);
    }
}