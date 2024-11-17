package com.ict.client.models;

public class ResponseModel {

    /**
     * This class is used for sending Response back.
     * Every Response has two common things: responseType and message
     * ResponseTypes:
     *      SUCCESS: Indicates that the request was successful
     *      FAILURE: Indicates that the request was a failure
     *      EMAIL_TAKEN: Indicates that if the user was trying to update the email, the given email is already taken
     *      ROLE_CHANGED: Indicates that the user's role was changed by admin and he should logout now
     */

    public static final String SUCCESS = "SUCCESS", FAILURE = "FAILURE", EMAIL_TAKEN = "EMAIL_TAKEN", ROLE_CHANGED="ROLE_CHANGED", INACTIVE="INACTIVE";

    private String responseType;
    private String message;

    public ResponseModel(String responseType) {
        this.responseType = responseType;
        this.message="";
    }

    public ResponseModel(String responseType, String message) {
        this.responseType = responseType;
        this.message = message;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
