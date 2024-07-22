package com.example.metricproducer.controller;

import com.example.metricproducer.model.Metric;
import com.example.metricproducer.service.MetricsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/metrics")
public class MetricsController {

    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @PostMapping
    public void sendMetric(@RequestBody Metric metric) {
        metricsService.sendMetric(metric);
    }
}
