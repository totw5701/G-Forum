package com.totwgforum.gforum.advice.exception;

public class CAuthorNotMatchedException extends RuntimeException {
    public CAuthorNotMatchedException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public CAuthorNotMatchedException(String msg) {
        super(msg);
    }

    public CAuthorNotMatchedException() {
        super();
    }
}
