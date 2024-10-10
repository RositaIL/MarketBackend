package pe.com.marbella.marketservice.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface TokenBlacklistService {
    void addToBlacklist(String token, Date expirationDate);
    boolean isTokenBlacklisted(String token);
    void removeExpiredTokens();
}
