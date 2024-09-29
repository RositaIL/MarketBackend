package pe.com.marbella.marketservice.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        String requestDescription = request.getDescription(false);
        String errorMessage = "Error interno del servidor: " + extractErrorMessage(ex);
        ErrorResponse errorDetails = new ErrorResponse(errorMessage, requestDescription);

        System.out.println(ex.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        String requestDescription = request.getDescription(false);
        String errorMessage = "Recurso no encontrado: " + extractErrorMessage(ex);
        ErrorResponse errorDetails = new ErrorResponse(errorMessage, requestDescription);

        System.out.println(ex.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder("Errores de validación:");
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.append("\n").append(error.getField()).append(": ").append(error.getDefaultMessage()));
        return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStockException(InsufficientStockException ex, WebRequest request) {
        String requestDescription = request.getDescription(false);
        String errorMessage = extractErrorMessage(ex);
        ErrorResponse errorDetails = new ErrorResponse(errorMessage, requestDescription);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        String requestDescription = request.getDescription(false);
        String errorMessage = extractErrorMessage(ex);
        ErrorResponse errorDetails = new ErrorResponse(errorMessage, requestDescription);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    private String extractErrorMessage(Exception ex) {
        String message = ex.getMessage();
        return message.substring(message.indexOf(":") + 1).trim();
    }
}
