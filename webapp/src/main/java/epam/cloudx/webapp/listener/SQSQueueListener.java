package epam.cloudx.webapp.listener;

import com.amazonaws.services.sqs.model.Message;
import epam.cloudx.webapp.service.CloudXSNSService;
import epam.cloudx.webapp.service.CloudxSQSService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SQSQueueListener {

    private final CloudxSQSService cloudxSQSService;
    private final CloudXSNSService cloudxSNSService;

    public SQSQueueListener(CloudxSQSService cloudxSQSService, CloudXSNSService cloudxSNSService) {
        this.cloudxSQSService = cloudxSQSService;
        this.cloudxSNSService = cloudxSNSService;
    }

    @Scheduled(fixedRate = 3000)
    public void readBatchFromQueueAndPushToTopic() {
        java.util.List<Message> messages = cloudxSQSService.readMessages();
        messages.forEach(message -> cloudxSNSService.sendMessageToTopic(message.getBody()));
    }
}
