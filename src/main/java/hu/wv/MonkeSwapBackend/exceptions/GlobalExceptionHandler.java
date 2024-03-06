package hu.wv.MonkeSwapBackend.exceptions;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    //Handler for exceptions that return with bad request
    //Status code 400
    @ExceptionHandler({
            IsEmptyException.class,
            IsRegisteredException.class,
            IllegalArgumentException.class})
    public ResponseEntity<String> handleBadRequestExceptions(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    //Handler for exceptions that return with not found
    //Status code 404
    @ExceptionHandler({
            BadCredentialsException.class,
            ObjectNotFoundException.class})
    public ResponseEntity<String> handleNotFoundExceptions(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
