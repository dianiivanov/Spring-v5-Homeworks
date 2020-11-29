package course.spring.cooking.recipes.web;

import course.spring.cooking.recipes.exception.EntityNotFoundException;
import course.spring.cooking.recipes.exception.InvalidDataException;
import course.spring.cooking.recipes.model.ErrorResponse;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = ErrorHandlingControllerAdvice.class)
public class ErrorHandlingControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidDataException(InvalidDataException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse(ex.getMessage(), ex.getViolations()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse(ex.getMessage()));
    }
}
