package pe.com.marbella.marketservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.marbella.marketservice.model.BlacklistedToken;
import pe.com.marbella.marketservice.repository.BlacklistedTokenRepository;
import pe.com.marbella.marketservice.service.TokenBlacklistService;

import java.util.Date;
import java.util.Optional;

/**
 * Implementación del servicio de lista negra de tokens.
 */
@Service
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    @Autowired
    private BlacklistedTokenRepository blacklistedTokenRepository;

    /**
     * Agrega un token a la lista negra.
     *
     * @param token         El token a agregar a la lista negra.
     * @param expirationDate La fecha de expiración del token.
     */
    @Override
    @Transactional
    public void addToBlacklist(String token, Date expirationDate) {
        BlacklistedToken blacklistedToken = new BlacklistedToken(token, expirationDate);
        blacklistedTokenRepository.save(blacklistedToken);
    }

    /**
     * Verifica si un token está en la lista negra.
     *
     * @param token El token a verificar.
     * @return true si el token está en la lista negra, false en caso contrario.
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isTokenBlacklisted(String token) {
        Optional<BlacklistedToken> blacklistedToken = blacklistedTokenRepository.findByToken(token);
        return blacklistedToken.isPresent();
    }

    /**
     * Elimina los tokens expirados de la lista negra.
     */
    @Override
    @Transactional
    public void removeExpiredTokens() {
        blacklistedTokenRepository.deleteByExpirationDateBefore(new Date());
    }

}

