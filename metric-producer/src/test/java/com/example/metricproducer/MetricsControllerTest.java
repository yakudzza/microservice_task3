package com.example.metricproducer;


import com.example.metricproducer.controller.MetricsController;
import com.example.metricproducer.model.Metric;
import com.example.metricproducer.service.MetricsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MetricsController.class)
public class MetricsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MetricsService metricsService;

    @Test
    void testSendMetric() throws Exception {
        Metric metric = new Metric(1L,"testMetric", "21"); // Пример создания Metric
        String metricJson = "{ \"name\": \"testMetric\", \"value\": 21 }"; // JSON-представление Metric

        mockMvc.perform(MockMvcRequestBuilders.post("/metrics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(metricJson))
                .andExpect(status().isOk());

        // Verify that the service method was called with the correct Metric
        verify(metricsService, times(1)).sendMetric(any(Metric.class));
    }
}
