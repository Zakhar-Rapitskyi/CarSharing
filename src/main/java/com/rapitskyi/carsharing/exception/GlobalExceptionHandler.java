package com.rapitskyi.carsharing.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex) {
        return ExceptionBuilder.buildExceptionResponse(ex, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleConflictException(ConflictException ex) {
        return ExceptionBuilder.buildExceptionResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        return ExceptionBuilder.buildExceptionResponse(
                new BadRequestException("Invalid request parameters"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        return ExceptionBuilder.buildExceptionResponse(
                new BadRequestException("Invalid credentials"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingParameter(MissingServletRequestParameterException ex) {
        String parameterName = ex.getParameterName();
        String errorMessage = "Required parameter is missing: " + parameterName;
        return ExceptionBuilder.buildExceptionResponse(
                new BadRequestException(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException ex) {
        return ExceptionBuilder.buildExceptionResponse(
                new NotFoundException("Element not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException(IOException ex) {
        return ExceptionBuilder.buildExceptionResponse(
                new ServerErrorException("Unable to operate with file"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UsernameNotFoundException ex) {
        return ExceptionBuilder.buildExceptionResponse(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException ex) {
        return ExceptionBuilder.buildExceptionResponse(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex) {
        return ExceptionBuilder.buildExceptionResponse(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<?> handleNumberFormatException(NumberFormatException ex) {
        return ExceptionBuilder.buildExceptionResponse(ex, HttpStatus.BAD_REQUEST);
    }
//    @ExceptionHandler(SQLServerException.class)
//    public ResponseEntity<?> handleSQLException(SQLServerException ex) {
//        return ExceptionBuilder.buildExceptionResponse(
//                new ServerErrorException("Application can not insert this data to database"),
//                HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}


