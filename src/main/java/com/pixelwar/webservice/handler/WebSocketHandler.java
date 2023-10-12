package com.pixelwar.webservice.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pixelwar.webservice.model.PixelData;
import com.pixelwar.webservice.service.MongoDBService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@AllArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final MongoDBService mongoDBService;
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Message reçu du client ici
        String payload = message.getPayload();
        // Transformation du payload en objet PixelData en utilisant ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(payload);

        if (jsonNode.has("type")) {
            String messageType = jsonNode.get("type").asText();

            if (messageType.equals("updatePixel")) {
                PixelData pixelData = objectMapper.convertValue(jsonNode.get("data"), PixelData.class);
                mongoDBService.saveData(pixelData);
            }
        }
        // Envoi d'une réponse au client
        // session.sendMessage(new TextMessage("Message reçu"));
    }
}
