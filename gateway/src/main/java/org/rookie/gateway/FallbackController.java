package org.rookie.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    @GetMapping("/fallback/user")
    public String userFallback() {
        return "User Service Fallback Response";
    }

    @GetMapping("/test/endpoint")
    public String testEndpoint() {
        return "Test Endpoint Response";
    }
}
