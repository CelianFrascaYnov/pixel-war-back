package com.pixelwar.webservice.handler;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocketHandler extends TextWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Gérer le message reçu du client ici
        String payload = message.getPayload();
        // Effectuer des opérations en réponse au message

        // Envoyer une réponse au client
        session.sendMessage(new TextMessage("Message reçu : " + payload));
    }
}
