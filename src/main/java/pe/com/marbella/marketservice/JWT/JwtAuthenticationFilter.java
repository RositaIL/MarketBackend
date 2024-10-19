package pe.com.marbella.marketservice.JWT;


import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.com.marbella.marketservice.exception.ErrorResponse;
import pe.com.marbella.marketservice.service.TokenBlacklistService;
import pe.com.marbella.marketservice.service.impl.AuthService;

/**
 * Filtro de autenticación JWT.
 * Este filtro se ejecuta una vez por solicitud y se encarga de:
 * 1. Extraer el token JWT de la cabecera Authorization.
 * 2. Validar el token JWT.
 * 3. Autenticar al usuario si el token es válido.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    AuthService authService;
    @Autowired
    JwtUtil jwtService;
    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    /**
     * Método principal del filtro.
     *
     * @param request     La solicitud HTTP.
     * @param response    La respuesta HTTP.
     * @param filterChain La cadena de filtros.
     * @throws ServletException Si ocurre una excepción Servlet.
     * @throws IOException      Si ocurre una excepción de E/S.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String token = getTokenFromRequest(request);
        final String username;
        if (token==null)
        {
            filterChain.doFilter(request, response);
            return;
        }
        // Verificar si el token está en la lista negra
        if (tokenBlacklistService.isTokenBlacklisted(token)) {
            String errorMessage = "Token invalidado, acceso no permitido.";
            JWTErrorManage(request, response, errorMessage, "Blacklisted token");
            return;
        }
        try {
            username = jwtService.getUsernameFromToken(token);
        } catch (ExpiredJwtException ex) {
            String errorMessage = "El token ha expirado.";
            JWTErrorManage(request, response, errorMessage, ex.getMessage());
            return;
        }catch (SignatureException ex) {
            String errorMessage = "La firma del token es inválida.";
            JWTErrorManage(request, response, errorMessage, ex.getMessage());
            return;
        }

        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails userDetails=this.authService.loadUserByUsername(username);
            if (jwtService.isTokenValid(token, userDetails))
            {
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }
        
        filterChain.doFilter(request, response);
    }

    /**
     * Maneja los errores relacionados con JWT que no llegan al GlobalExceptionHandler.
     *
     * @param request         La solicitud HTTP.
     * @param response        La respuesta HTTP.
     * @param errorMessage    El mensaje de error principal.
     * @param message         El mensaje de error detallado.
     * @throws IOException    Si ocurre una excepción de E/S.
     */
    private void JWTErrorManage(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, String errorMessage, String message) throws IOException {
        String requestDescription = request.getRequestURI();
        ErrorResponse errorDetails = new ErrorResponse(errorMessage, requestDescription);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();
        String jsonErrorResponse = mapper.writeValueAsString(errorDetails);

        response.getWriter().write(jsonErrorResponse);
        System.out.println(message);
    }

    /**
     * Obtiene el token JWT de la solicitud HTTP.
     *
     * @param request La solicitud HTTP.
     * @return El token JWT o null si no se encuentra.
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer "))
        {
            return authHeader.substring(7);
        }
        return null;
    }
}