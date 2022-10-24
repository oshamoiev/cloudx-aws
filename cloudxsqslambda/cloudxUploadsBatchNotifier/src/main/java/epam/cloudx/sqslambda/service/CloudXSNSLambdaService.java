package epam.cloudx.sqslambda.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;

public class CloudXSNSLambdaService {

    private static final String SNS_TOPIC_ARN = "arn:aws:sns:us-east-1:366246053967:uploads-notification-topic";

    private final AmazonSNS amazonSNS;

    public CloudXSNSLambdaService() {
        this.amazonSNS = AmazonSNSClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
    }

    public void sendMessageToTopic(String message) {
        PublishRequest publishRequest = new PublishRequest()
                .withMessage(message)
                .withTopicArn(SNS_TOPIC_ARN);

        amazonSNS.publish(publishRequest);
    }
}
