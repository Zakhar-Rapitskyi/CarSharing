package com.rapitskyi.carsharing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionBuilder {
    public static ResponseEntity<ExceptionDetails> buildExceptionResponse(Exception e, HttpStatus httpStatus) {
        ExceptionDetails details = ExceptionDetails.builder()
                .HTTPCODE(httpStatus.value())
                .HTTPSTATUS(httpStatus)
                .MESSAGE(e.getMessage())
                .build();
        return new ResponseEntity<>(details, httpStatus);
    }
}
