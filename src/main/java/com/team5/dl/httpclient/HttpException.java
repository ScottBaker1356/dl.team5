package com.team5.dl.httpclient;

public class HttpException extends RuntimeException {

    public static final String MESSAGE_FORMAT = "{ \"message\" :  \"%s\" }";
    public static final String MESSAGE_SERVER_ERROR = "{ \"message\" :  \"server error\" }";
    public static final String MESSAGE_INVALID_URL_PARAMETER = "{ \"message\" :  \"invalid url parameter\" }";
    public static final String MESSAGE_INVALID_QUERY_PARAMETER = "{ \"message\" :  \"invalid query parameter\" }";
    public static final String MESSAGE_INVALID_REQUEST = "{ \"message\" :  \"invalid request\" }";
    public static final String MESSAGE_NOT_FOUND = "{ \"message\" : \"not found\" }";
    public static final String OPERATION_NOT_ALLOWED = "{ \"message\" : \"operation not allowed\" }";

    private int code;
    private HttpStatusType statusType;

    public HttpException(String message, int code) {
        super(message);

        this.code = code;

        if (code < 200) {
            this.statusType = HttpStatusType.INFORMATIONAL;
        } else if (code < 300) {
            this.statusType = HttpStatusType.SUCCESS;
        } else if (code < 400) {
            this.statusType = HttpStatusType.REDIRECTION;
        } else if (code < 500) {
            this.statusType = HttpStatusType.CLIENT_ERROR;
        } else {
            this.statusType = HttpStatusType.SERVER_ERROR;
        }
    }

    public int getCode() {
        return code;
    }

    public HttpStatusType getStatusType() {
        return statusType;
    }
}
