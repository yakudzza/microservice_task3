package com.example.metricconsumer.controller;

import com.example.metricconsumer.model.Metric;
import com.example.metricconsumer.repository.MetricRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metrics")
public class MetricsQueryController {

    private final MetricRepository metricRepository;

    public MetricsQueryController(MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
    }

    @GetMapping
    public List<Metric> getAllMetrics() {
        return metricRepository.findAll();
    }

    @GetMapping("/{id}")
    public Metric getMetricById(@PathVariable String id) {
        return metricRepository.findById(id).orElse(null);
    }
}
