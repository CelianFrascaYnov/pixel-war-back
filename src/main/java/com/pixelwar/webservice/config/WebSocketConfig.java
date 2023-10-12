package com.pixelwar.webservice.config;

import com.pixelwar.webservice.handler.WebSocketHandler;
import com.pixelwar.webservice.service.MongoDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

/**
 * Configuration de la gestion des WebSockets.
 */
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    /**
     * Service pour la gestion des WebSockets.
     */
    private final MongoDBService mongoDBService;

    /**
     * Configuration des gestionnaires de WebSockets.
     *
     * @param registry Registre des gestionnaires de WebSockets.
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandler(mongoDBService), "/ws").setAllowedOrigins("*");
    }
}
