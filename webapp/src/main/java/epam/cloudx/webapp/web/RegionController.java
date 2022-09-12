package epam.cloudx.webapp.web;

import com.amazonaws.util.EC2MetadataUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("region")
public class RegionController {

    @GetMapping("test")
    public Map<String, String> getRegionName() {
        return Map.of("region", "test-name");
    }

    @GetMapping("name-az")
    public Map<String, String> getRegionAndAZ() {
        HashMap<String, String> response = new HashMap<>();
        response.put("region", EC2MetadataUtils.getEC2InstanceRegion());
        response.put("az", EC2MetadataUtils.getAvailabilityZone());

        return response;
    }
}

