package com.studyroom.studyroom.advice.exception;

public class CustomResourceNotExistException extends RuntimeException {
    public CustomResourceNotExistException(String msg, Throwable t) {
        super(msg, t);
    }
    public CustomResourceNotExistException(String msg) {
        super(msg);
    }
    public CustomResourceNotExistException() {
        super();
    }
}
