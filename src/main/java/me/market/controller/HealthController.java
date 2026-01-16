package me.market.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {
    @GetMapping("/health-check")
    public Map<String, String> healthController() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return response;
    }
}
