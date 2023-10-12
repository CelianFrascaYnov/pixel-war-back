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

/**
 * Gestionnaire des connexions WebSocket.
 */
@AllArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    /**
     * Service pour la gestion de la base de données.
     */
    private final MongoDBService mongoDBService;

    /**
     * Liste des connexions actives.
     */
    private final List<WebSocketSession> activeSessions = new ArrayList<>();

    /**
     * Méthode exécutée après qu'une connexion WebSocket ait été établie avec succès.
     *
     * @param session Session WebSocket nouvellement établie.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        activeSessions.add(session);
    }

    /**
     * Méthode exécutée après qu'une connexion WebSocket ait été fermée.
     *
     * @param session Session WebSocket fermée.
     * @param status  Raison de la fermeture de la session.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        activeSessions.remove(session);
    }

    /**
     * Gère les messages WebSocket entrants, traite les actions telles que l'ajout de pixels, et envoie les mises à jour
     * à toutes les connexions actives.
     *
     * @param session  Session WebSocket à partir de laquelle le message a été reçu.
     * @param message  Message WebSocket entrant.
     * @throws Exception En cas d'erreur lors de la gestion du message.
     */
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
