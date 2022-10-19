package epam.cloudx.webapp.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CloudxSQSService {

    @Value("${app.amazon.sqsURL}")
    private String sqsUrl;

    private final AmazonSQS amazonSQS;

    public CloudxSQSService(AmazonSQS amazonSQS) {
        this.amazonSQS = amazonSQS;
    }

    public void sendMessage(String message) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(sqsUrl)
                .withMessageBody(message)
                .withDelaySeconds(5);
        amazonSQS.sendMessage(sendMessageRequest);
    }

    public List<Message> readMessages() {
        ReceiveMessageRequest request = new ReceiveMessageRequest()
                .withQueueUrl(sqsUrl)
                .withWaitTimeSeconds(10)
                .withMaxNumberOfMessages(10);

        ReceiveMessageResult receiveMessageResult = amazonSQS.receiveMessage(request);
        List<Message> messages = receiveMessageResult.getMessages();

        messages.stream()
                .map(Message::getReceiptHandle)
                .forEach(receipt -> amazonSQS.deleteMessage(sqsUrl, receipt));

        return messages;
    }
}
