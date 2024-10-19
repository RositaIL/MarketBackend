package pe.com.marbella.marketservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.service.TokenBlacklistService;

/**
 * Servicio para limpiar los tokens JWT expirados de la lista negra.
 */
@Service
public class BlackListCleanupService {

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    /**
     * Tarea programada para limpiar los tokens expirados de la lista negra.
     * Se ejecuta cada 24 horas (86400000 milisegundos).
     */
    @Scheduled(fixedRate = 86400000)
    public void cleanExpiredTokens() {
        tokenBlacklistService.removeExpiredTokens();
    }
}

