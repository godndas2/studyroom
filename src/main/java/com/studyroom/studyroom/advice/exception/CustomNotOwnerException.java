package com.studyroom.studyroom.advice.exception;

public class CustomNotOwnerException extends RuntimeException{

    private static final long serialVersionUID = 2241549550934267615L;

    public CustomNotOwnerException(String msg, Throwable t) {
        super(msg, t);
    }
    public CustomNotOwnerException(String msg) {
        super(msg);
    }
    public CustomNotOwnerException() {
        super();
    }
}
