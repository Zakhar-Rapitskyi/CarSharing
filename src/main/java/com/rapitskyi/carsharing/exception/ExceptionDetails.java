package com.rapitskyi.carsharing.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ExceptionDetails {

    private final Integer HTTPCODE;
    private final String MESSAGE;
    private final HttpStatus HTTPSTATUS;
}
