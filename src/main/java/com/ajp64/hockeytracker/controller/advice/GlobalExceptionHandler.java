package com.ajp64.hockeytracker.controller.advice;

import com.ajp64.hockeytracker.exceptions.NoNameException;
import com.ajp64.hockeytracker.exceptions.EntityNotFoundException;
import com.ajp64.hockeytracker.model.ErrorDetails;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoNameException.class)
    public ResponseEntity<ErrorDetails> exceptionNoNameHandler()
    {
    ErrorDetails errorDetails = new ErrorDetails();

    return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDetails> exceptionPlayerNotFoundHandler(EntityNotFoundException ex)
    {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(ex.getMessage());

        return ResponseEntity.badRequest().body(errorDetails);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle("Validation failed");
        problemDetail.setDetail("Request body contains invalid or missing fields");

        problemDetail.setProperty("errors", extractErrors(ex.getBindingResult().getAllErrors()));

        return ResponseEntity.status(status.value()).body(problemDetail);
    }

    /**
     * Utility method to format field and object errors consistently.
     */
    private List<Map<String, String>> extractErrors(List<ObjectError> objectErrors) {
        List<Map<String, String>> errors = new ArrayList<>();

        for (ObjectError error : objectErrors) {
            if (error instanceof FieldError fieldError) {
                errors.add(Map.of(
                        "field", fieldError.getField(),
                        "message", fieldError.getDefaultMessage()
                ));
            } else {
                // Object-level error (e.g., @AssertTrue, cross-field validation)
                errors.add(Map.of(
                        "object", error.getObjectName(),
                        "message", error.getDefaultMessage()
                ));
            }
        }

        return errors;
    }

}
