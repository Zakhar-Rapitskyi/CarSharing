package com.rapitskyi.carsharing.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }
}
