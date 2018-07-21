package com.team5.dl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.team5.dl.domain.TextMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SMSServiceProcessor implements Processor {

    private final static Logger LOGGER = LoggerFactory.getLogger(SMSServiceProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        LOGGER.debug("Entering " + this.getClass().getName());

        String messageBody = exchange.getIn().getBody(String.class);
        TextMessage textMessage = JavaJsonSerializationController.deserialize(messageBody, TextMessage.class);
        send(textMessage);

        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);

        LOGGER.debug("Leaving " + this.getClass().getName());

    }

    public static void send(TextMessage textMessage) {



        AwsClientBuilder snsClientBuilder = AmazonSNSClientBuilder.standard();
        AWSCredentialsProvider credentialsProvider = new AWSCredentialsProvider() {
            @Override
            public AWSCredentials getCredentials() {
                //dlSvcAcct
                String accessKey = "AKIAJQ3PWBSEYGEDIUEQ";
                String secretKey = "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";
                BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

                return credentials;
            }

            @Override
            public void refresh() {

            }
        };

        snsClientBuilder.setCredentials(credentialsProvider);
        snsClientBuilder.setEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("sns.us-east-1.amazonaws.com", "us-east-1"));
        AmazonSNS snsClient = (AmazonSNS)snsClientBuilder.build();

        String message = textMessage.getMessage();
        String phoneNumber = textMessage.getPhoneNumbers().get(0);
        Map<String, MessageAttributeValue> smsAttributes = new HashMap<String, MessageAttributeValue>();

        //<set SMS attributes>
        smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
                .withStringValue("IFBI") //The sender ID shown on the device.
                .withDataType("String"));
        smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
                .withStringValue("0.50") //Sets the max price to 0.50 USD.
                .withDataType("Number"));
        smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
                .withStringValue("Promotional") //Sets the type to promotional.
                .withDataType("String"));

        sendSMSMessage((AmazonSNSClient)snsClient, message, phoneNumber, smsAttributes);
    }

    public static void sendSMSMessage(AmazonSNSClient snsClient, String message,
                                      String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
        PublishResult result = snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes)
        );
        System.out.println(result); // Prints the message ID.
    }

}
