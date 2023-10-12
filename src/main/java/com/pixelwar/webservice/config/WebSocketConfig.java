package com.pixelwar.webservice.config;

import com.pixelwar.webservice.handler.WebSocketHandler;
import com.pixelwar.webservice.service.MongoDBService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final MongoDBService mongoDBService;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandler(mongoDBService), "/ws").setAllowedOrigins("*");
    }
}
