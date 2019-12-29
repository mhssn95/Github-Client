package com.mhssn.githubclient.model.resource;

public class Response<T> {
    private ResponseState state;
    private T data;
    private String message;

    public Response(ResponseState state, T data, String message) {
        this.state = state;
        this.data = data;
        this.message = message;
    }

    public ResponseState getState() {
        return state;
    }

    public void setState(ResponseState state) {
        this.state = state;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
