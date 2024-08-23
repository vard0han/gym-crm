package com.example.actuators;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class CustomMetrics {

    private final Counter requestCounter;

    public CustomMetrics(MeterRegistry meterRegistry) {
        this.requestCounter = Counter.builder("custom_requests_total")
                .description("Total number of custom requests")
                .register(meterRegistry);
    }

    public void incrementRequestCounter() {
        this.requestCounter.increment();
    }
}
