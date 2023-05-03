package com.sparta.myblog.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(final CustomException e) {
        log.error("handleCustomException: {}", e.getStatusEnum());
        return ResponseEntity
                .status(e.getStatusEnum().getStatus().value())
                .body(new ErrorResponse(e.getStatusEnum()));
    }
}
