package epam.cloudx.webapp.service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.ListSubscriptionsByTopicResult;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.Subscription;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CloudXSNSService {

    @Value("${app.amazon.snsTopicARN}")
    private String snsTopicARN;

    private final AmazonSNS amazonSNS;

    public CloudXSNSService(AmazonSNS amazonSNS) {
        this.amazonSNS = amazonSNS;
    }

    public void subscribe(String email) {
        SubscribeRequest subscribeRequest = new SubscribeRequest(snsTopicARN, "email", email);
        amazonSNS.subscribe(subscribeRequest);
    }

    public void unsubscribe(String email) {
        ListSubscriptionsByTopicResult listResult = amazonSNS.listSubscriptionsByTopic(snsTopicARN);
        List<Subscription> subscriptions = listResult.getSubscriptions();
        subscriptions.stream()
                .filter(subscription -> email.equals(subscription.getEndpoint()))
                .findAny()
                .ifPresent(subscription -> amazonSNS.unsubscribe(subscription.getSubscriptionArn()));
    }

    public void sendMessageToTopic(String message) {
        PublishRequest publishRequest = new PublishRequest()
                .withMessage(message)
                .withTopicArn(snsTopicARN);

        amazonSNS.publish(publishRequest);
    }
}
