package com.pixelwar.webservice.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Configuration pour la connexion à MongoDB.
 */
@Configuration
@EnableMongoRepositories(basePackages =  "com.pixelwar.webservice.repository")
public class MongoDBConfig {

    /**
     * Bean pour le client MongoDB.
     *
     * @return Client MongoDB.
     */
    @Bean
    public MongoClient mongoClient() {
        String connectionString = "mongodb+srv://celianfrasca:celianfrasca@cluster0.zyg3uzy.mongodb.net/?retryWrites=true&w=majority";

        ConnectionString connectionStr = new ConnectionString(connectionString);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionStr)
                .build();

        return MongoClients.create(settings);
    }

    /**
     * Bean pour le template MongoDB.
     *
     * @return Template MongoDB.
     */
    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "pixelwarDB");
    }
}
