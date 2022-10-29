package com.sqs.service;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.services.sqs.AmazonSQS;
import com.sqs.config.SQSConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
@RequiredArgsConstructor
public class JMSService {

    @Value("${aws.sqs.queue-name}")
    private String queueName;

    private final SQSConfig sqsConfig;

    public void postJMSMessage() {

        AmazonSQS sqsClient = sqsConfig.amazonSQS();

        SQSConnectionFactory connectionFactory
                = new SQSConnectionFactory(new ProviderConfiguration(), sqsClient);

        try {
            SQSConnection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);

            //Create producer
            MessageProducer producer = session.createProducer(queue);
            TextMessage message = session.createTextMessage("Great");

            producer.send(message);

            System.out.println("Message has sent " + message.getJMSMessageID());

        } catch (JMSException e) {
            System.out.println("JMSException " + e);
        }
    }

    public void consumeJMSMessage() {
        AmazonSQS sqsClient = sqsConfig.amazonSQS();

        SQSConnectionFactory connectionFactory
                = new SQSConnectionFactory(new ProviderConfiguration(), sqsClient);

        try {
            SQSConnection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);

            //Create consumer
            MessageConsumer consumer = session.createConsumer(queue);

            connection.start();
            Message receiveMessage = consumer.receive(1000);

            if(receiveMessage != null) {
                System.out.println(((TextMessage) receiveMessage).getText());
            }

        } catch (JMSException e) {
            System.out.println("JMSException " + e);
        }
    }
}
