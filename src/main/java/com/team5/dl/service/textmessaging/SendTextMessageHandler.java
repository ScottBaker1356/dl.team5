package com.team5.dl.service.textmessaging;

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
import com.team5.dl.JavaJsonSerializationController;
import com.team5.dl.service.ServiceHandler;
import com.team5.dl.domain.TextMessage;
import com.team5.dl.httpclient.HttpRequest;
import com.team5.dl.httpclient.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendTextMessageHandler extends ServiceHandler {

    private String message;
    private List<String> phoneNumbers;

    public SendTextMessageHandler(HttpRequest httpRequest) {
        super(httpRequest);
        String body = httpRequest.getBody().get();
        TextMessage textMessage = JavaJsonSerializationController.deserialize(body, TextMessage.class);
        message = textMessage.getMessage();
        phoneNumbers = textMessage.getPhoneNumbers();
    }

    @Override
    public HttpResponse run() {

        AWSCredentialsProvider credentialsProvider = getCredentialsProvider();

        for(String phoneNumber : phoneNumbers) {
            send(message, phoneNumber, credentialsProvider);
        }

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setHttpResponseCode(200);
        //httpResponse.getHeaders().put("Content-Type", "application/json");
        return httpResponse;
    }

    private AWSCredentialsProvider getCredentialsProvider() {
        return new AWSCredentialsProvider() {
            @Override
            public AWSCredentials getCredentials() {
                return new BasicAWSCredentials(accessKey, secretKey);
            }

            @Override
            public void refresh() {

            }
        };
    }

    private void send(String message, String phoneNumber, AWSCredentialsProvider credentialsProvider) {

        AwsClientBuilder snsClientBuilder = AmazonSNSClientBuilder.standard();
        snsClientBuilder.setCredentials(credentialsProvider);
        snsClientBuilder.setEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(serviceEndpoint, signingRegion));

        AmazonSNS snsClient = (AmazonSNS)snsClientBuilder.build();

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

    private static void sendSMSMessage(AmazonSNSClient snsClient, String message,
                                      String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
        PublishResult result = snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes)
        );
        //System.out.println(result); // Prints the message ID.
    }

}
