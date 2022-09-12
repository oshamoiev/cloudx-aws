package epam.cloudx.webapp.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("region")
public class RegionController {
    @GetMapping("name")
    public Map<String, String> getRegionName() {
        return Map.of("regionName", "test-name");
    }
}

