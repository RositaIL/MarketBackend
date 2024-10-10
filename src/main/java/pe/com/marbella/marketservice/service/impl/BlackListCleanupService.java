package pe.com.marbella.marketservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.service.TokenBlacklistService;

@Service
public class BlackListCleanupService {

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Scheduled(fixedRate = 86400000)
    public void cleanExpiredTokens() {
        tokenBlacklistService.removeExpiredTokens();
    }
}

