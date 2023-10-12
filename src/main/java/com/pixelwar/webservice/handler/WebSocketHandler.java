package com.pixelwar.webservice.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pixelwar.webservice.model.PixelData;
import com.pixelwar.webservice.service.MongoDBService;
import lombok.AllArgsConstructor;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final MongoDBService mongoDBService;

    private final List<WebSocketSession> activeSessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        activeSessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        activeSessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Message reçu du client ici
        String payload = message.getPayload();
        // Transformation du payload en objet PixelData en utilisant ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(payload);

        if (jsonNode.has("type")) {
            String messageType = jsonNode.get("type").asText();

            if (messageType.equals("ajoutPixel")) {
                PixelData pixelData = objectMapper.convertValue(jsonNode.get("data"), PixelData.class);
                mongoDBService.saveData(pixelData);
                // Envois de la liste mise à jour à toutes les connexions actives
                for (WebSocketSession activeSession : activeSessions) {
                    activeSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(mongoDBService.getAllPixels())));
                }
            } else if (messageType.equals("getAllPixels")) {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(mongoDBService.getAllPixels())));
            }
        }
    }
}
