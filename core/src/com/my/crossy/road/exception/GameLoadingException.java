package com.my.crossy.road.exception;

/**
 * Created by ldalzotto on 06/04/2017.
 */
public class GameLoadingException extends RuntimeException {
    public GameLoadingException(String message, Throwable cause){
        super(message,cause);
    }
}
