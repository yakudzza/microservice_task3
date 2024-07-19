package com.example.metricconsumer.controller;

import com.example.metricconsumer.model.Metric;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MetricController {

    private List<Metric> metrics = new ArrayList<>();

    @GetMapping("/metrics")
    public List<Metric> getAllMetrics() {
        return metrics;
    }

    @GetMapping("/metrics/{id}")
    public Metric getMetricById(@PathVariable int id) {
        return metrics.get(id);
    }

    public void addMetric(Metric metric) {
        metrics.add(metric);
    }
}
