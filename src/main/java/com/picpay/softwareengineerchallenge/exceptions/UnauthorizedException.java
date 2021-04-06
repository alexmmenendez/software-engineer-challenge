package com.picpay.softwareengineerchallenge.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String s, Throwable throwable) { super(s, throwable); }
}
