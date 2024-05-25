package com.marvel.exception;

public class MarvelException extends Exception {

    public MarvelException() {
    }

    public MarvelException(String message) {
        super(message);
    }

    public MarvelException(String message, Throwable cause) {
        super(message, cause);
    }
}
