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

/**
 * Manejador global de excepciones para la aplicación.
 * Captura y maneja diferentes tipos de excepciones, devolviendo respuestas HTTP apropiadas con detalles del error.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja cualquier excepción no controlada.
     *
     * @param ex      La excepción que ocurrió.
     * @param request La solicitud web actual.
     * @return Una respuesta HTTP 500 (INTERNAL_SERVER_ERROR) con un mensaje de error genérico.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        return buildErrorResponse("Error interno del servidor", request, HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    /**
     * Maneja la excepción EntityNotFoundException, que se lanza cuando no se encuentra una entidad en la base de datos.
     *
     * @param ex      La excepción EntityNotFoundException.
     * @param request La solicitud web actual.
     * @return Una respuesta HTTP 404 (NOT_FOUND) con un mensaje indicando que el recurso no fue encontrado.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        String mensajeError = "Recurso no encontrado: " + extractErrorMessage(ex);
        return buildErrorResponse(mensajeError, request, HttpStatus.NOT_FOUND, ex);
    }

    /**
     * Maneja la excepción MethodArgumentNotValidException, que se lanza cuando falla la validación de los argumentos de un método.
     *
     * @param ex      La excepción MethodArgumentNotValidException.
     * @param request La solicitud web actual.
     * @return Una respuesta HTTP 400 (BAD_REQUEST) con una lista de los errores de validación.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        StringBuilder errores = new StringBuilder("Errores de validación: ");
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.append(error.getDefaultMessage()));
        String mensajeError = errores.toString();
        return buildErrorResponse(mensajeError,request, HttpStatus.BAD_REQUEST, ex);
    }

    /**
     * Maneja la excepción InsufficientStockException, que se lanza cuando no hay suficiente stock para un producto.
     *
     * @param ex      La excepción InsufficientStockException.
     * @param request La solicitud web actual.
     * @return Una respuesta HTTP 400 (BAD_REQUEST) con un mensaje indicando que no hay suficiente stock.
     */
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStockException(InsufficientStockException ex, WebRequest request) {
        String mensajeError = extractErrorMessage(ex);
        return buildErrorResponse(mensajeError, request, HttpStatus.BAD_REQUEST, ex);
    }

    /**
     * Maneja la excepción IllegalArgumentException, que se lanza cuando se proporciona un argumento ilegal a un método.
     *
     * @param ex      La excepción IllegalArgumentException.
     * @param request La solicitud web actual.
     * @return Una respuesta HTTP 400 (BAD_REQUEST) con un mensaje indicando que el argumento es ilegal.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        String mensajeError = extractErrorMessage(ex);
        return buildErrorResponse(mensajeError, request, HttpStatus.BAD_REQUEST, ex);
    }

    /**
     * Maneja la excepción HttpMessageNotReadableException, que se lanza cuando no se puede leer el cuerpo de la solicitud HTTP.
     *
     * @param ex      La excepción HttpMessageNotReadableException.
     * @param request La solicitud web actual.
     * @return Una respuesta HTTP 400 (BAD_REQUEST) con un mensaje indicando que el cuerpo de la solicitud es incorrecto.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
        String mensajeError = "La solicitud no contiene el cuerpo requerido o el formato es incorrecto.";
        return buildErrorResponse(mensajeError, request, HttpStatus.BAD_REQUEST, ex);
    }

    /**
     * Maneja la excepción MethodArgumentTypeMismatchException, que se lanza cuando el tipo de argumento de un método no coincide con el esperado.
     *
     * @param ex      La excepción MethodArgumentTypeMismatchException.
     * @param request La solicitud web actual.
     * @return Una respuesta HTTP 400 (BAD_REQUEST) con un mensaje indicando que el tipo de argumento no es válido.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String mensajeError = "Tipo de argumento no válido: " + ex.getName() + ". Se esperaba un valor de tipo " + Objects.requireNonNull(ex.getRequiredType()).getSimpleName() + ".";
        return buildErrorResponse(mensajeError, request, HttpStatus.BAD_REQUEST, ex);
    }

    /**
     * Maneja la excepción NoResourceFoundException, que se lanza cuando no se encuentra un recurso.
     *
     * @param ex      La excepción NoResourceFoundException.
     * @param request La solicitud web actual.
     * @return Una respuesta HTTP 404 (NOT_FOUND) con un mensaje indicando que el recurso no fue encontrado.
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex, WebRequest request) {
        String mensajeError = "No se encontró el recurso solicitado: " + ex.getMessage();
        return buildErrorResponse(mensajeError, request, HttpStatus.NOT_FOUND, ex);
    }

    /**
     * Maneja la excepción BadCredentialsException, que se lanza cuando las credenciales de autenticación son inválidas.
     *
     * @param ex      La excepción BadCredentialsException.
     * @param request La solicitud web actual.
     * @return Una respuesta HTTP 401 (UNAUTHORIZED) con un mensaje indicando que las credenciales son inválidas.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        String mensajeError = "Credenciales inválidas.";
        return buildErrorResponse(mensajeError, request, HttpStatus.UNAUTHORIZED, ex);
    }

    /**
     * Maneja la excepción ExpiredJwtException, que se lanza cuando un token JWT ha expirado.
     *
     * @param ex      La excepción ExpiredJwtException.
     * @param request La solicitud web actual.
     * @return Una respuesta HTTP 401 (UNAUTHORIZED) con un mensaje indicando que el token ha expirado.
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
        String mensajeError = "El token ha expirado.";
        return buildErrorResponse(mensajeError, request, HttpStatus.UNAUTHORIZED, ex);
    }

    /**
     * Maneja la excepción SignatureException, que se lanza cuando la firma de un token JWT es inválida.
     *
     * @param ex      La excepción SignatureException.
     * @param request La solicitud web actual.
     * @return Una respuesta HTTP 401 (UNAUTHORIZED) con un mensaje indicando que la firma del token es inválida.
     */
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleSignatureException(SignatureException ex, WebRequest request) {
        String mensajeError = "La firma del token es inválida.";
        return buildErrorResponse(mensajeError, request, HttpStatus.UNAUTHORIZED, ex);
    }

    /**
     * Maneja la excepción DataIntegrityViolationException, que se lanza cuando se viola una restricción de integridad de datos.
     *
     * @param ex      La excepción DataIntegrityViolationException.
     * @param request La solicitud web actual.
     * @return Una respuesta HTTP 409 (CONFLICT) con un mensaje indicando que se ha producido un error de integridad de datos.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String mensajeError = obtenerMensajeError(ex.getMessage());
        return buildErrorResponse(mensajeError, request, HttpStatus.CONFLICT, ex);
    }

    /**
     * Maneja las excepciones AccessDeniedException y AuthorizationDeniedException, que se lanzan cuando se deniega el acceso a un recurso.
     *
     * @param ex      La excepción AccessDeniedException o AuthorizationDeniedException.
     * @param request La solicitud web actual.
     * @return Una respuesta HTTP 403 (FORBIDDEN) con un mensaje indicando que el acceso está denegado.
     */
    @ExceptionHandler({AccessDeniedException.class, AuthorizationDeniedException.class})
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(RuntimeException ex, WebRequest request) {
        String mensajeError = "Acceso denegado: No tienes permisos para acceder a este recurso.";
        return buildErrorResponse(mensajeError, request, HttpStatus.FORBIDDEN, ex);
    }

    /**
     * Maneja la excepción MissingRequestHeaderException, que se lanza cuando falta una cabecera requerida en la solicitud.
     *
     * @param ex      La excepción MissingRequestHeaderException.
     * @param request La solicitud web actual.
     * @return Una respuesta HTTP 400 (BAD_REQUEST) con un mensaje indicando que falta la cabecera requerida.
     */
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException ex, WebRequest request) {
        String mensajeError = "La cabecera requerida '" + ex.getHeaderName() + "' no está presente.";
        return buildErrorResponse(mensajeError, request, HttpStatus.BAD_REQUEST, ex);
    }

    /**
     * Construye una respuesta de error con la información proporcionada.
     *
     * @param mensajeError       El mensaje de error.
     * @param request            La solicitud web actual.
     * @param status             El código de estado HTTP.
     * @param ex                La excepción que ocurrió.
     * @return Una respuesta ResponseEntity con la información del error.
     */
    private ResponseEntity<ErrorResponse> buildErrorResponse(String mensajeError, WebRequest request, HttpStatus status, Exception ex) {
        String requestDescription = request.getDescription(false);
        ErrorResponse errorDetails = new ErrorResponse(mensajeError, requestDescription);
        System.out.println(ex.getMessage());
        return new ResponseEntity<>(errorDetails, status);
    }

    /**
     * Obtiene un mensaje de error más descriptivo a partir del mensaje original de la excepción DataIntegrityViolationException.
     *
     * @param mensajeOriginal El mensaje de error original de la excepción.
     * @return Un mensaje de error más descriptivo.
     */
    private String obtenerMensajeError(String mensajeOriginal) {
        if (mensajeOriginal.contains("Duplicate entry")) {
            String valorDuplicado = mensajeOriginal.split("'")[1];
            return "El valor ingresado '" + valorDuplicado + "' ya existe.";
        } else {
            return "Error de integridad de datos";
        }
    }

    /**
     * Extrae el mensaje de error principal de una excepción.
     *
     * @param ex La excepción de la que se extrae el mensaje.
     * @return El mensaje de error principal.
     */
    private String extractErrorMessage(Exception ex) {
        String message = ex.getMessage();
        return message != null && message.contains(":") ? message.substring(message.indexOf(":") + 1).trim() : message;
    }
}
