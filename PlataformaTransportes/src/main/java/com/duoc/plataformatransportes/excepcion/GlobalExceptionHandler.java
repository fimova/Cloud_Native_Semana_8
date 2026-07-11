package com.duoc.plataformatransportes.excepcion;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ResponseEntity<ErrorResponse>
    handleValidation(
            MethodArgumentNotValidException ex
    ) {

        String mensaje =
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(
                                FieldError::getDefaultMessage
                        )
                        .collect(
                                Collectors.joining(
                                        ", "
                                )
                        );

        return ResponseEntity
                .badRequest()
                .body(
                        construirError(
                                HttpStatus.BAD_REQUEST,
                                mensaje
                        )
                );

    }

    @ExceptionHandler(
            IllegalArgumentException.class
    )
    public ResponseEntity<ErrorResponse>
    handleIllegalArgument(
            IllegalArgumentException ex
    ) {

        return ResponseEntity
                .badRequest()
                .body(
                        construirError(
                                HttpStatus.BAD_REQUEST,
                                ex.getMessage()
                        )
                );

    }

        @ExceptionHandler(
                RuntimeException.class
        )
        public ResponseEntity<ErrorResponse>
        handleRuntime(
                RuntimeException ex
        ) {

        ex.printStackTrace();

        return ResponseEntity
                .status(
                        HttpStatus.INTERNAL_SERVER_ERROR
                )
                .body(
                        construirError(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                ex.getMessage()
                        )
                );

        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<Map<String, Object>> manejarGeneral(
                Exception ex
        ) {

        ex.printStackTrace();

        Map<String, Object> body =
                new HashMap<>();

        body.put(
                "error",
                ex.getClass()
                        .getSimpleName()
        );

        body.put(
                "message",
                ex.getMessage()
        );

        body.put(
                "status",
                500
        );

        return ResponseEntity
                .internalServerError()
                .body(
                        body
                );

        }

    private ErrorResponse construirError(
            HttpStatus status,
            String mensaje
    ) {

        return ErrorResponse
                .builder()
                .timestamp(
                        LocalDateTime.now()
                )
                .status(
                        status.value()
                )
                .error(
                        status.getReasonPhrase()
                )
                .message(
                        mensaje
                )
                .build();

    }

}