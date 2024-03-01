package com.gitee.leica.exception;

/**
 * @author wyh
 * @since 2021/4/6 17:38
 */
public class InternalException extends RuntimeException {

    public InternalException(String message, Throwable e) {
        super(message, e);
    }
}
