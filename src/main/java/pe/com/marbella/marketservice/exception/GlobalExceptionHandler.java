package pe.com.marbella.marketservice.exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.security.SignatureException;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        String requestDescription = request.getDescription(false);
        String errorMessage = "Error interno del servidor";
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
        String errorMessage = "La solicitud no contiene el cuerpo requerido o el formato es incorrecto.";
        String requestDescription = request.getDescription(false);
        ErrorResponse errorDetails = new ErrorResponse(errorMessage, requestDescription);

        System.out.println(ex.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String requestDescription = request.getDescription(false);
        String errorMessage = "Tipo de argumento no válido: " + ex.getName() + ". Se esperaba un valor de tipo " + Objects.requireNonNull(ex.getRequiredType()).getSimpleName() + ".";
        ErrorResponse errorDetails = new ErrorResponse(errorMessage, requestDescription);

        System.out.println(ex.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex, WebRequest request) {
        String requestDescription = request.getDescription(false);
        String errorMessage = "No se encontró el recurso solicitado: " + ex.getMessage();
        ErrorResponse errorDetails = new ErrorResponse(errorMessage, requestDescription);

        System.out.println(ex.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        String requestDescription = request.getDescription(false);
        String errorMessage = "Credenciales inválidas.";
        ErrorResponse errorDetails = new ErrorResponse(errorMessage, requestDescription);

        System.out.println(ex.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
        String requestDescription = request.getDescription(false);
        String errorMessage = "El token ha expirado.";
        ErrorResponse errorDetails = new ErrorResponse(errorMessage, requestDescription);

        System.out.println(ex.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleSignatureException(SignatureException ex, WebRequest request) {
        String requestDescription = request.getDescription(false);
        String errorMessage = "La firma del token es inválida.";
        ErrorResponse errorDetails = new ErrorResponse(errorMessage, requestDescription);

        System.out.println(ex.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String requestDescription = request.getDescription(false);
        String mensajeError = "Error de integridad de datos: " + obtenerMensajeError(ex.getMessage());
        ErrorResponse errorDetails = new ErrorResponse(mensajeError, requestDescription);

        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    private String obtenerMensajeError(String mensajeOriginal) {
        if (mensajeOriginal.contains("Duplicate entry")) {
            String valorDuplicado = mensajeOriginal.split("'")[1];
            return "El valor ingresado '" + valorDuplicado + "' ya existe.";
        }
        return mensajeOriginal;
    }

    private String extractErrorMessage(Exception ex) {
        String message = ex.getMessage();
        return message.substring(message.indexOf(":") + 1).trim();
    }
}
