package epam.cloudx.webapp.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping(value = "/")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok().build();
    }
}
