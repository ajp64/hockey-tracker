package controller.advice;

import exceptions.NoPlayerNameException;
import model.ErrorDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(NoPlayerNameException.class)
    public ResponseEntity<ErrorDetails> exceptionNoPlayerNameHandler()
    {
    ErrorDetails errorDetails = new ErrorDetails();
    errorDetails.setMessage("No player name provided.");

    return ResponseEntity.badRequest().body(errorDetails);
    }

}
