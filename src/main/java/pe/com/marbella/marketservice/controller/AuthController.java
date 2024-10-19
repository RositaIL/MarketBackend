package pe.com.marbella.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import pe.com.marbella.marketservice.JWT.JwtUtil;
import pe.com.marbella.marketservice.dto.AuthLogin;
import pe.com.marbella.marketservice.model.Usuario;
import pe.com.marbella.marketservice.repository.UsuarioRepository;
import pe.com.marbella.marketservice.service.TokenBlacklistService;

import java.util.Date;

/**
 * Controlador para manejar las solicitudes de autenticación.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    /**
     * Crea un token JWT para un usuario válido.
     *
     * @param authRequest Solicitud de autenticación que contiene el nombre de usuario y la contraseña.
     * @return Un token JWT si la autenticación es exitosa.
     * @throws Exception Si ocurre un error durante la autenticación.
     */
    @PostMapping("/login")
    public String createToken(@RequestBody AuthLogin authRequest) throws Exception {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
        );

        final Usuario user = userRepository.findUsuarioByUsername(authRequest.username()).orElseThrow();
        return jwtUtil.getToken(user);
    }

    /**
     * Cierra la sesión del usuario invalidando el token JWT.
     *
     * @param authHeader Encabezado de autorización que contiene el token JWT.
     * @return Una respuesta que indica si el cierre de sesión fue exitoso.
     * @throws Exception Si ocurre un error al invalidar el token.
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) throws Exception {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Date expirationDate = jwtUtil.getExpiration(token);
            tokenBlacklistService.addToBlacklist(token, expirationDate);
            return ResponseEntity.ok("Logout exitoso. Token invalidado.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token no presente en el encabezado.");
        }
    }

}