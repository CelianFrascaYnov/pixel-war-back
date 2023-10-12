package com.pixelwar.webservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MongoDBService {

    private final MongoTemplate mongoTemplate;

    public void saveData() {
        mongoTemplate.save("data");
    }
}
