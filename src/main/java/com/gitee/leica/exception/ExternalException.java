package com.gitee.leica.exception;

/**
 * @author wyh
 * @since 2021/4/6 17:38
 */
public class ExternalException extends RuntimeException {

    public ExternalException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
