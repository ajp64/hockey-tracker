package controller.advice;

import exceptions.NoPlayerNameException;
import exceptions.PlayerNotFoundException;
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

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<ErrorDetails> exceptionPlayerNotFoundHandler(PlayerNotFoundException ex)
    {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(ex.getMessage());

        return ResponseEntity.badRequest().body(errorDetails);
    }

}
