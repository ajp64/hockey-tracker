package com.ajp64.hockeytracker.controller.advice;

import com.ajp64.hockeytracker.exceptions.NoNameException;
import com.ajp64.hockeytracker.exceptions.EntityNotFoundException;
import com.ajp64.hockeytracker.model.ErrorDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(NoNameException.class)
    public ResponseEntity<ErrorDetails> exceptionNoPlayerNameHandler()
    {
    ErrorDetails errorDetails = new ErrorDetails();
    errorDetails.setMessage("No player name provided.");

    return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDetails> exceptionPlayerNotFoundHandler(EntityNotFoundException ex)
    {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(ex.getMessage());

        return ResponseEntity.badRequest().body(errorDetails);
    }

}
