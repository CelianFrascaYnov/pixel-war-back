package com.pixelwar.webservice.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pixelwar")
@Getter
@Setter
public class PixelData {
    @Id
    private String id; // L'identifiant unique de l'objet PixelData
    private String color;
    private int x;
    private int y;


    // Constructeur par défaut sans paramètres
    public PixelData() {
    }

    public PixelData(String color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }
}
