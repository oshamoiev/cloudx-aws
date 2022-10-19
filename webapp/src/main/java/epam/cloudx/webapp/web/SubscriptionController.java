package epam.cloudx.webapp.web;

import epam.cloudx.webapp.service.CloudXSNSService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("subscriptions")
public class SubscriptionController {

    private final CloudXSNSService cloudxSNSService;

    public SubscriptionController(CloudXSNSService cloudxSNSService) {
        this.cloudxSNSService = cloudxSNSService;
    }

    @GetMapping("subscribe/{email}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void subscribe(@PathVariable String email) {
        cloudxSNSService.subscribe(email);
    }

    @GetMapping("unsubscribe/{email}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void unsubscribe(@PathVariable String email) {
        cloudxSNSService.unsubscribe(email);
    }
}
