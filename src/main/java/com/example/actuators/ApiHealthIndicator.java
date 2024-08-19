package com.example.actuators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiHealthIndicator implements HealthIndicator {
    private final RestTemplate restTemplate;

    public ApiHealthIndicator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Health health() {
        String url = "http://localhost:8080";
        try{
            restTemplate.getForObject(url, String.class);
            return Health.up().withDetail("API", "Available").build();
        }catch (Exception e){
            return Health.down(e).withDetail("API", "Not reachable").build();
        }
    }
}
