package com.picpay.softwareengineerchallenge.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ExceptionCustomHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> badCredentialsExceptionHandler(final BadCredentialsException e) {
        log.info("Unauthorized request  [{}]", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new GeneralExceptionError(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> unauthorizedExceptionHandler(final UnauthorizedException e) {
        log.info("Unauthorized request  [{}]", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new GeneralExceptionError(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> badRequestExceptionHandler(final BadRequestException e) {
        log.info("Bad request  [{}]", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GeneralExceptionError(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> missingServletRequestParameterExceptionHandler(final MissingServletRequestParameterException e) {
        log.info("Bad request  [{}]", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GeneralExceptionError(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> constraintViolationExceptionHandler(final ConstraintViolationException contraingViolation) {
        return new HashMap<>() {{
            put("status", HttpStatus.BAD_REQUEST.value());
            put("message", String.format(
                    "Invalid fields %s",
                    contraingViolation.getConstraintViolations().stream().map(violation ->
                            violation.getPropertyPath().toString().isBlank() ?
                                    String.format("undefined: %s", violation.getMessage()) :
                                    StringUtils.substringAfterLast(violation.getPropertyPath().toString(), ".").concat(": ").concat(violation.getMessage())
                    ).distinct().collect(Collectors.toList())
            ));
        }};
    }
}
