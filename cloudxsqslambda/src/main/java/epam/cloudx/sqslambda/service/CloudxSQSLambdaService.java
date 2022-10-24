package epam.cloudx.sqslambda.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

import java.util.List;

public class CloudxSQSLambdaService {

    private static final String SQS_URL = "https://sqs.us-east-1.amazonaws.com/366246053967/uploads-notification-queue";

    private final AmazonSQS amazonSQS;

    public CloudxSQSLambdaService() {
        this.amazonSQS = AmazonSQSClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
    }

    public List<Message> readMessages() {
        ReceiveMessageRequest request = new ReceiveMessageRequest()
                .withQueueUrl(SQS_URL)
                .withWaitTimeSeconds(10)
                .withMaxNumberOfMessages(10);

        ReceiveMessageResult receiveMessageResult = amazonSQS.receiveMessage(request);
        List<Message> messages = receiveMessageResult.getMessages();

        messages.stream()
                .map(Message::getReceiptHandle)
                .forEach(receipt -> amazonSQS.deleteMessage(SQS_URL, receipt));

        return messages;
    }
}
