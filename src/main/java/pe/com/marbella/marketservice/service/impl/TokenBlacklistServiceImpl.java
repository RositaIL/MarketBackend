package pe.com.marbella.marketservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.marbella.marketservice.model.BlacklistedToken;
import pe.com.marbella.marketservice.repository.BlacklistedTokenRepository;
import pe.com.marbella.marketservice.service.TokenBlacklistService;

import java.util.Date;
import java.util.Optional;

@Service
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    @Autowired
    private BlacklistedTokenRepository blacklistedTokenRepository;

    @Override
    @Transactional
    public void addToBlacklist(String token, Date expirationDate) {
        BlacklistedToken blacklistedToken = new BlacklistedToken(token, expirationDate);
        blacklistedTokenRepository.save(blacklistedToken);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isTokenBlacklisted(String token) {
        Optional<BlacklistedToken> blacklistedToken = blacklistedTokenRepository.findByToken(token);
        return blacklistedToken.isPresent();
    }

    @Override
    @Transactional
    public void removeExpiredTokens() {
        blacklistedTokenRepository.deleteByExpirationDateBefore(new Date());
    }

}

