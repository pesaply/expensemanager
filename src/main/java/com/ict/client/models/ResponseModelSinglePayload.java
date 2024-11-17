package com.ict.client.models;

public class ResponseModelSinglePayload<T> extends ResponseModel{

    /**
     * This is a type of Response which contains an additional Payload ("result") which is a certain class defined by the generic type T.
     * This class is used in places where the request has to return a json payload of generic type T (For example, user data)
     */

    private T result;

    public ResponseModelSinglePayload(String responseType, T result) {
        super(responseType);
        this.result=result;
    }

    public ResponseModelSinglePayload(String responseType, String message, T result) {
        super(responseType, message);
        this.result=result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
