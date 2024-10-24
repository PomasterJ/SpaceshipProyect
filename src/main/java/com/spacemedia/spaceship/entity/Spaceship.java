package com.spacemedia.spaceship.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@Entity
public class Spaceship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Spaceship(){

    }

    public Spaceship(String name) {
        this.name = name;
    }
}
