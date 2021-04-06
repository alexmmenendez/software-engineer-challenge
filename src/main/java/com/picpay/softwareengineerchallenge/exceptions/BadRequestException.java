package com.picpay.softwareengineerchallenge.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(final String message) {
        super(message);
    }
    public BadRequestException(String s, Throwable throwable) { super(s, throwable); }
}
