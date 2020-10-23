package website.marcioheleno.ajax_api.resources.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import website.marcioheleno.ajax_api.services.exceptions.DatabaseException;
import website.marcioheleno.ajax_api.services.exceptions.ResourceNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandartError> entityNotFound(ResourceNotFoundException e, HttpServletRequest servletRequest) {
        var status = HttpStatus.NOT_FOUND;
        var standartError = StandartError
                .builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Resource not found")
                .message(e.getMessage())
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(status).body(standartError);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandartError> dataBase(DatabaseException e, HttpServletRequest servletRequest) {
        var status = HttpStatus.BAD_REQUEST;
        var standartError = StandartError
                .builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error("Database exception")
                .message(e.getMessage())
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(status).body(standartError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest servletRequest) {
        var status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError error = new  ValidationError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Validation exception");
        error.setMessage(e.getMessage());
        error.setPath(servletRequest.getRequestURI());

        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            error.addError(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(error);
    }

}
