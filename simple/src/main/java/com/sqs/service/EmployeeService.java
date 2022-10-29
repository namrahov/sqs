package com.sqs.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.sqs.config.SQSConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    @Value("${aws.sqs.queue-url}")
    private String queueUrl;

    private final SQSConfig sqsConfig;

    public void sendMessageToEmployee() {
        AmazonSQS sqsClient = sqsConfig.amazonSQS();

        final SendMessageRequest message = new SendMessageRequest();
        message.setMessageBody("Hi bro");
        message.setQueueUrl(queueUrl);

        sqsClient.sendMessage(message);
    }

    public void receiveMessage() {
        AmazonSQS sqsClient = sqsConfig.amazonSQS();
        final ReceiveMessageRequest receiveRequest = new ReceiveMessageRequest()
                .withMaxNumberOfMessages(1)
                .withQueueUrl(queueUrl);

        final ReceiveMessageResult messageResult = sqsClient.receiveMessage(receiveRequest);

        //System.out.println(messageResult.getMessages().stream().findFirst().get().getBody());
        messageResult.getMessages()
                .forEach(System.out::println);

    }
}
