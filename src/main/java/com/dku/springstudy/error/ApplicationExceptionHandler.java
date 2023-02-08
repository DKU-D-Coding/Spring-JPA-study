package com.dku.springstudy.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleApplicationException(final ApplicationException exception) {
        log.error("handleApplicationException", exception);
        final ErrorCode errorCode = exception.getErrorCode();
        final ErrorResponse response = ErrorResponse.from(errorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getHttpStatus()));
    }
}
