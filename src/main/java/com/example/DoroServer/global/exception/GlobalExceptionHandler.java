package com.example.DoroServer.global.exception;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못 할 경우 발생 - @Valid, @Validated
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e,
            HttpServletRequest request) {
        log.error("MethodArgumentNotValidException: {} - {}", e.getMessage(), request.getRequestURL());
        final ErrorResponse response = ErrorResponse.of(Code.METHOD_ARGUMENT_NOT_VALID, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    // @ModelAttribute 으로 binding error 발생시 BindException
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(
            BindException e, HttpServletRequest request) {
        log.error("BindException: {} - {}", e.getMessage(), request.getRequestURL());
        final ErrorResponse response = ErrorResponse.of(
                Code.METHOD_ARGUMENT_NOT_VALID, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    // Enum type 일치하지 않아 binding 못할 경우 발생
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e,
            HttpServletRequest request) {
        log.error("MethodArgumentTypeMismatchException: {} - {}", e.getMessage(), request.getRequestURL());
        final ErrorResponse response = ErrorResponse.of(e);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 지원하지 않은 HTTP method 호출 할 경우 발생
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e,
            HttpServletRequest request) {
        log.error("HttpRequestMethodNotSupportedException: {} - {}", e.getMessage(), request.getRequestURL());
        final ErrorResponse response = ErrorResponse.of(Code.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ErrorResponse> handleBaseException(
            BaseException e,
            HttpServletRequest request) {
        log.error("BaseException: {} - {}", e.getCode().getMessage(), request.getRequestURL());
        final Code errorCode = e.getCode();
        final ErrorResponse response = ErrorResponse.of(errorCode);
        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(
            Exception e, HttpServletRequest request) {
        log.error("Exception: {} {}", request.getRequestURL(), e);
        final ErrorResponse response = ErrorResponse.of(Code.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
