package pe.com.marbella.marketservice.exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        return buildErrorResponse("Error interno del servidor", request, HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        String mensajeError = "Recurso no encontrado: " + extractErrorMessage(ex);
        return buildErrorResponse(mensajeError, request, HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        StringBuilder errores = new StringBuilder("Errores de validación: ");
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.append(error.getDefaultMessage()));
        String mensajeError = errores.toString();
        return buildErrorResponse(mensajeError,request, HttpStatus.BAD_REQUEST, ex);
    }


    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStockException(InsufficientStockException ex, WebRequest request) {
        String mensajeError = extractErrorMessage(ex);
        return buildErrorResponse(mensajeError, request, HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        String mensajeError = extractErrorMessage(ex);
        return buildErrorResponse(mensajeError, request, HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
        String mensajeError = "La solicitud no contiene el cuerpo requerido o el formato es incorrecto.";
        return buildErrorResponse(mensajeError, request, HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String mensajeError = "Tipo de argumento no válido: " + ex.getName() + ". Se esperaba un valor de tipo " + Objects.requireNonNull(ex.getRequiredType()).getSimpleName() + ".";
        return buildErrorResponse(mensajeError, request, HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex, WebRequest request) {
        String mensajeError = "No se encontró el recurso solicitado: " + ex.getMessage();
        return buildErrorResponse(mensajeError, request, HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        String mensajeError = "Credenciales inválidas.";
        return buildErrorResponse(mensajeError, request, HttpStatus.UNAUTHORIZED, ex);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
        String mensajeError = "El token ha expirado.";
        return buildErrorResponse(mensajeError, request, HttpStatus.UNAUTHORIZED, ex);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleSignatureException(SignatureException ex, WebRequest request) {
        String mensajeError = "La firma del token es inválida.";
        return buildErrorResponse(mensajeError, request, HttpStatus.UNAUTHORIZED, ex);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String mensajeError = "Error de integridad de datos";
        return buildErrorResponse(mensajeError, request, HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler({AccessDeniedException.class, AuthorizationDeniedException.class})
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(RuntimeException ex, WebRequest request) {
        String mensajeError = "Acceso denegado: No tienes permisos para acceder a este recurso.";
        return buildErrorResponse(mensajeError, request, HttpStatus.FORBIDDEN, ex);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException ex, WebRequest request) {
        String mensajeError = "La cabecera requerida '" + ex.getHeaderName() + "' no está presente.";
        return buildErrorResponse(mensajeError, request, HttpStatus.BAD_REQUEST, ex);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String mensajeError, WebRequest request, HttpStatus status, Exception ex) {
        String requestDescription = request.getDescription(false);
        ErrorResponse errorDetails = new ErrorResponse(mensajeError, requestDescription);
        System.out.println(ex.getMessage());
        return new ResponseEntity<>(errorDetails, status);
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
        return message != null && message.contains(":") ? message.substring(message.indexOf(":") + 1).trim() : message;
    }
}
