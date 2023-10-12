package com.pixelwar.webservice.service;

import com.pixelwar.webservice.model.PixelData;
import com.pixelwar.webservice.repository.MongoDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des données dans MongoDB.
 */
@Service
@RequiredArgsConstructor
public class MongoDBService {

    /**
     * Repository pour la gestion des données dans MongoDB.
     */
    private final MongoDBRepository mongoDBRepository;

    /**
     * Enregistre un pixel dans la base de données MongoDB. S'il existe déjà un pixel avec les mêmes coordonnées (x, y),
     * met à jour sa couleur, sinon insère un nouveau pixel.
     *
     * @param pixelData Pixel à enregistrer.
     */
    public void saveData(PixelData pixelData) {
        // Recherche d'un pixel existant avec les mêmes valeurs de x et y
        PixelData existingPixel = mongoDBRepository.findByXAndY(pixelData.getX(), pixelData.getY());

        if (existingPixel != null) {
            // Mise à jour de la couleur du pixel existant
            existingPixel.setColor(pixelData.getColor());
            mongoDBRepository.save(existingPixel); // Mise à jour du pixel déjà existant
        } else {
            // Aucun pixel existant trouvé, insertion d'un nouveau pixel
            mongoDBRepository.save(pixelData);
        }
    }

    /**
     * Récupère tous les pixels enregistrés en base de données.
     *
     * @return Liste de tous les pixels enregistrés en base de données.
     */
    public List<PixelData> getAllPixels() {
        // Récupèration de tous les pixels existants en base de données
        return mongoDBRepository.findAll();
    }
}
