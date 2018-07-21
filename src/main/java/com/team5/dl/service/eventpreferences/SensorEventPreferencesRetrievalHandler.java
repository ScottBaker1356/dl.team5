package com.team5.dl.service.eventpreferences;

import com.team5.dl.JavaJsonSerializationController;
import com.team5.dl.domain.Customer;
import com.team5.dl.domain.Customers;
import com.team5.dl.httpclient.*;
import com.team5.dl.service.ServiceHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SensorEventPreferencesRetrievalHandler extends ServiceHandler {

    private Map<String, Customer> customerMap = new ConcurrentHashMap<>();

    public SensorEventPreferencesRetrievalHandler(HttpRequest httpRequest) {
        super(httpRequest);
    }

    @Override
    public HttpResponse run() {


        HttpResponse httpResponse = retrieveCustomers();

        return httpResponse;
    }

    private HttpResponse retrieveCustomers() {
        String customersHost = "https://s3-us-west-2.amazonaws.com";
        String customersPath = "/dl.team5.com.customers/customerEventPreferences.json";

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setMethod(HttpMethod.GET);
        httpRequest.setHostname(customersHost);
        httpRequest.setPath(customersPath);

        HttpClient httpClient = new GenericHttpClient(httpRequest);

        HttpResponse httpResponse = httpClient.call();

        if(404 == httpResponse.getHttpResponseCode() || !httpResponse.getBody().isPresent()) {
            throw new HttpException("",404);
        }

        httpResponse.getHeaders().put("Content-Type", "application/json");

        Customers customers = JavaJsonSerializationController.deserialize(httpResponse.getBody().get(), Customers.class);
        for(Customer customer : customers.getCustomers()) {
            customerMap.put(customer.getCustomerId(), customer);
        }
        return httpResponse;
    }

}
