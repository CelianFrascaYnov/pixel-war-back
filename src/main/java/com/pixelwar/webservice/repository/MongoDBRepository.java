package com.pixelwar.webservice.repository;

import com.pixelwar.webservice.model.PixelData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDBRepository extends MongoRepository<PixelData, String> {
    PixelData findByXAndY(int x, int y);
}
