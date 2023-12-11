package com.example.testproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DogDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dog_id")
    private String dogId;

    @Column(name = "name")
    private String name;
    @Column(name = "weight")
    private String weight;
    @Column(name = "height")
    private String height;
    @Column(name = "life_span")
    private String lifeSpan;
    @Column(name = "bred_for")
    private String bredFor;
    @Column(name = "breed_group")
    private String breedGroup;

}
