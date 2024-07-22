package com.example.metricconsumer;

import com.example.metricconsumer.controller.MetricsQueryController;
import com.example.metricconsumer.model.Metric;
import com.example.metricconsumer.repository.MetricRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(MetricsQueryController.class)
public class MetricsQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MetricRepository metricRepository;

    @BeforeEach
    void setUp() {
        Metric metric1 = new Metric(1L, "Type1", "Value1");
        Metric metric2 = new Metric(2L, "Type2", "Value2");

        when(metricRepository.findAll()).thenReturn(Arrays.asList(metric1, metric2));
        when(metricRepository.findById(1L)).thenReturn(Optional.of(metric1));
        when(metricRepository.findById(2L)).thenReturn(Optional.of(metric2));
        when(metricRepository.findById(999L)).thenReturn(Optional.empty());
    }

    @Test
    void testGetAllMetrics() throws Exception {
        mockMvc.perform(get("/metrics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].type").value("Type1"))
                .andExpect(jsonPath("$[0].value").value("Value1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].type").value("Type2"))
                .andExpect(jsonPath("$[1].value").value("Value2"));
    }

    @Test
    void testGetMetricById() throws Exception {
        mockMvc.perform(get("/metrics/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("Type1"))
                .andExpect(jsonPath("$.value").value("Value1"));
    }

    @Test
    void testGetMetricByIdNotFound() throws Exception {
        mockMvc.perform(get("/metrics/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
