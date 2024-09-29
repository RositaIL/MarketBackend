package pe.com.marbella.marketservice.exception;

public record ErrorResponse(
        String error,
        String requestDescription) {
}
