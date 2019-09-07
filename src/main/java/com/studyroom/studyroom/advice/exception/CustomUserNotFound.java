package com.studyroom.studyroom.advice.exception;

public class CustomUserNotFound extends RuntimeException{
    public CustomUserNotFound(String msg, Throwable t) {
        super(msg,t);
    }
    public CustomUserNotFound(String msg) {
        super(msg);
    }
    public CustomUserNotFound() {
        super();
    }
}
