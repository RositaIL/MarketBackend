package pe.com.marbella.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.marbella.marketservice.JWT.JwtUtil;
import pe.com.marbella.marketservice.dto.AuthLogin;
import pe.com.marbella.marketservice.model.Usuario;
import pe.com.marbella.marketservice.repository.UsuarioRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository userRepository;


    @PostMapping("/login")
    public String createToken(@RequestBody AuthLogin authRequest) throws Exception {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        final Usuario user = userRepository.findUsuarioByUsername(authRequest.getUsername()).orElseThrow();
        return jwtUtil.getToken(user);
    }
}