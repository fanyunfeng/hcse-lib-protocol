package com.hcse.service;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    public ServiceException() {

    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
