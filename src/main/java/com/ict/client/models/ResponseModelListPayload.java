package com.ict.client.models;

import java.util.List;

public class ResponseModelListPayload<T> extends ResponseModel{

    /**
     * This is a type of Response which contains an additional Payload ("results") which is a List
     * This class is used in places where the request has to return a list of things (For example, list of expenses )
     */

    private List<T> results;

    public ResponseModelListPayload(String responseType, List<T> results) {
        super(responseType);
        this.results = results;
    }

    public ResponseModelListPayload(String responseType, String message, List<T> results) {
        super(responseType, message);
        this.results=results;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
