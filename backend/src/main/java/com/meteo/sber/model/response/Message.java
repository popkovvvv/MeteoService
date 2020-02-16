package com.meteo.sber.model.request;

public class Message {

    private  String message;

    private int status;

    public Message( String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

}
