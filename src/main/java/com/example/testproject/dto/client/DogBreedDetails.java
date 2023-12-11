package com.example.testproject.dto.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DogBreedDetails {

    @JsonProperty(value = "id")
    private String id;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "weight")
    private Weight weight;
    @JsonProperty(value = "height")
    private String height;
    @JsonProperty(value = "life_span")
    private String lifeSpan;
    @JsonProperty(value = "bred_for")
    private String bredFor;
    @JsonProperty(value = "breed_group")
    private String breedGroup;
}
