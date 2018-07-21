package com.team5.dl.httpclient;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class GenericHttpClient extends HttpClient {

    private static final String UTF_8_NAME;

    static {
        UTF_8_NAME = StandardCharsets.UTF_8.name();
    }

    public GenericHttpClient(HttpRequest request) {
        super(request);
    }

    @Override
    public HttpResponse call() {
        return call(request, 1);
    }

    private HttpResponse call(HttpRequest request, int attempt) {

        logger.debug(request.toString());

        HttpURLConnection connection = null;

        try {
            connection = acquireConnection(request);
            connection.setRequestMethod(request.getMethod().toString());
            if (request.getMethod().hasOutput()) {
                connection.setDoOutput(true);
                writeRequestBody(connection, request.getBody().get());
            }

            HttpResponse response = new HttpResponse();
            int httpResponseCode = makeHttpCall(connection);
            response.setHttpResponseCode(httpResponseCode);
            response.setMessage(connection.getResponseMessage());
            response.setBody(getResponseBody(connection, httpResponseCode));
            connection.disconnect();

            logger.debug("Response on first attempt: \n" + response.toString());

            if (response.getHttpResponseCode() == 401 && attempt < 2) {
                ++attempt;
                HttpResponse responseOnRetry = call(request, attempt);
                logger.debug("Response on attempt " + attempt + ": \n" + response.toString());
                return responseOnRetry;
            } else {
                return response;
            }


        } catch (Exception e) {
            logger.debug(e.getClass().getCanonicalName() + ": ", e);
            throw new HttpException(HttpException.MESSAGE_SERVER_ERROR, 500);
        } finally {
            if(null != connection) {
                connection.disconnect();
            }
        }
    }

    private HttpURLConnection acquireConnection(HttpRequest request) throws IOException {

        String uri = buildURI(request);

        HttpURLConnection connection = acquireConnection(uri);
        Map<String, String> headers = request.getHeaders();
        Iterator headersIterator = headers.keySet().iterator();

        while(headersIterator.hasNext()) {
            String header = (String)headersIterator.next();
            connection.setRequestProperty(header, (String)headers.get(header));
        }

        return connection;
    }

    private String buildURI(HttpRequest request) throws UnsupportedEncodingException {

        String uri = request.getHostname() + request.getPath();

        Map<String, String> queryParameters = request.getQueryParameters();
        Iterator queryParametersIterator = queryParameters.keySet().iterator();

        boolean isFirst = true;
        while(queryParametersIterator.hasNext()) {
            String queryParameterKey = (String)queryParametersIterator.next();
            String queryParameterValue = (String) queryParameters.get(queryParameterKey);
            if(isFirst) {
                uri += "?";
                isFirst = false;
            } else {
                uri += "&";
            }
            uri += queryParameterKey + "=" + queryParameterValue;
        }

        uri = uri.replaceAll("\\s", "%20");

        return uri;
    }

    protected HttpURLConnection acquireConnection(String uri) throws IOException {
        HttpURLConnection connection;
        URLConnection urlConnection = (new URL(uri)).openConnection();
        if(uri.startsWith("https")) {
            connection = (HttpsURLConnection) urlConnection;
        } else {
            connection = (HttpURLConnection) urlConnection;
        }
        connection.setRequestProperty("Accept-Charset", UTF_8_NAME);
        return connection;
    }

    private void writeRequestBody(HttpURLConnection connection, String requestBody) throws IOException {
        OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream());
        Throwable throwable = null;

        try {
            output.write(requestBody);
        } catch (Throwable t) {
            throwable = t;
            throw t;
        } finally {
            if (output != null) {
                if (throwable != null) {
                    try {
                        output.close();
                    } catch (Throwable t) {
                        throwable.addSuppressed(t);
                    }
                } else {
                    output.close();
                }
            }

        }

    }

    private int makeHttpCall(HttpURLConnection connection) throws IOException {
        //Calling getResponseCode() is what actually makes the http call; this is an oddity of the java.net libraries.
        return connection.getResponseCode();
    }

    private String getResponseBody(HttpURLConnection connection, int httpResponseCode) throws IOException {
        String responseBody = "";
        if (httpResponseCode == 200) {
            InputStream response = connection.getInputStream();
            Scanner scanner = new Scanner(response);
            Throwable throwable = null;

            try {
                responseBody = scanner.useDelimiter("\\A").next();
            } catch (Throwable t) {
                throwable = t;
                throw t;
            } finally {
                if (scanner != null) {
                    if (throwable != null) {
                        try {
                            scanner.close();
                        } catch (Throwable t) {
                            throwable.addSuppressed(t);
                        }
                    } else {
                        scanner.close();
                    }
                }

            }
        }

        return responseBody;
    }



}
