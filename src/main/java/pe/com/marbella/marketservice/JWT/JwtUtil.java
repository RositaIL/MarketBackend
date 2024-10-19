package pe.com.marbella.marketservice.JWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import pe.com.marbella.marketservice.model.Usuario;

/**
 * Utilidad para manejar tokens JWT (JSON Web Token).
 */
@Component
public class JwtUtil {
    private static final String SECRET_KEY="586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    /**
     * Genera un token JWT para el usuario dado.
     *
     * @param user El usuario para el cual se genera el token.
     * @return El token JWT generado.
     */
    public String getToken(Usuario user) {
        return getToken(new HashMap<>(), user);
    }

    /**
     * Genera un token JWT para el usuario dado con reclamos adicionales.
     *
     * @param extraClaims Reclamos adicionales para incluir en el token.
     * @param usuario     El usuario para el cual se genera el token.
     * @return El token JWT generado.
     */
    private String getToken(Map<String, Object> extraClaims, Usuario usuario) {
        String rol = usuario.getRol().getNombreRol().name();

        return Jwts.builder()
                .claims(extraClaims)
                .claim("userID", usuario.getIdUsuario())
                .claim("nombre", usuario.getNombresApellidosUsu())
                .claim("correo", usuario.getEmailUsu())
                .claim("rol", rol)
                .subject(usuario.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2)) //2 horas
                .signWith(getKey())
                .compact();
    }

    /**
     * Obtiene la clave secreta utilizada para firmar el token.
     *
     * @return La clave secreta.
     */
    private SecretKey getKey() {
       byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
       return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Obtiene el nombre de usuario del token JWT.
     *
     * @param token El token JWT.
     * @return El nombre de usuario del token.
     */
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Verifica si el token JWT es válido para el usuario dado.
     *
     * @param token       El token JWT.
     * @param userDetails Los detalles del usuario.
     * @return true si el token es válido, false en caso contrario.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username=getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    /**
     * Obtiene todos los reclamos del token JWT.
     *
     * @param token El token JWT.
     * @return Los reclamos del token.
     */
    private Claims getAllClaims(String token)
    {
        return Jwts
            .parser()
            .verifyWith(getKey())
            .clockSkewSeconds(30)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    /**
     * Obtiene un reclamo específico del token JWT.
     *
     * @param token          El token JWT.
     * @param claimsResolver Una función que extrae el reclamo deseado de los reclamos del token.
     * @param <T>            El tipo del reclamo.
     * @return El reclamo deseado.
     */
    public <T> T getClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Obtiene la fecha de expiración del token JWT.
     *
     * @param token El token JWT.
     * @return La fecha de expiración del token.
     */
    public Date getExpiration(String token)
    {
        return getClaim(token, Claims::getExpiration);
    }

    /**
     * Verifica si el token JWT ha expirado.
     *
     * @param token El token JWT.
     * @return true si el token ha expirado, false en caso contrario.
     */
    private boolean isTokenExpired(String token)
    {
        return getExpiration(token).before(new Date());
    }
    
}