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
        try {
            mongoDBRepository.save(pixelData);
        } catch (Exception e) {
            System.out.println("Erreur : " + e);
        }
    }

    public List<PixelData> getAllPixels() {
        // Récupèration de tous les pixels existants en base de données
        return mongoDBRepository.findAll();
    }
}
