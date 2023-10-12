package com.pixelwar.webservice.service;

import com.pixelwar.webservice.model.PixelData;
import com.pixelwar.webservice.repository.MongoDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MongoDBService {

    private final MongoDBRepository mongoDBRepository;

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

    public List<PixelData> getAllPixels() {
        // Récupèration de tous les pixels existants en base de données
        return mongoDBRepository.findAll();
    }
}
