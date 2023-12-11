package com.example.testproject.repository;

import com.example.testproject.config.RestTemplateConfig;
import com.example.testproject.dto.client.DogBreedDetails;
import com.example.testproject.dto.client.DogBreedDto;
import com.example.testproject.dto.client.PlaceHolderDto;
import com.example.testproject.entity.DogDetails;
import com.example.testproject.service.TestRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class TestService {

    @Value("${api.host}")
    private String apiHost;

    @Value("${json.place.holder.url}")
    private String placeHolderApi;

    @Value("${movie.db.url}")
    private String movieDbApi;

    @Value("${api.key}")
    private String apiKey;

    @Value("${movie.api.key}")
    private String movieDbApiKey;

    @Value("${spring.application.name}")
    private String serviceName;

    private final RestTemplate restTemplate;

    private TestRepository testRepository;

    public TestService(@Qualifier(RestTemplateConfig.DEFAULT_REST_TEMPLATE) RestTemplate restTemplate, TestRepository testRepository) {
        this.restTemplate = restTemplate;
        this.testRepository = testRepository;
    }


    public List<DogBreedDto> getDogBreeds(int limit, int page){

        String url = String.format("%s/breeds", apiHost);
        String builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("limit", limit)
                .queryParam("page", page)
                .encode()
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-api-key", apiKey);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);


        ResponseEntity<List<DogBreedDto>> response = restTemplate.exchange(
                builder,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<List<DogBreedDto>>() {}
        );

        return response.getBody();

    }

    public DogBreedDetails getDogBreedDetails(String breedId){

        String url = String.format("%s/breeds/%s", apiHost,breedId);
        String builder = UriComponentsBuilder.fromHttpUrl(url)
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-api-key", apiKey);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);


        ResponseEntity<DogBreedDetails> response = restTemplate.exchange(
                builder,
                HttpMethod.GET,
                httpEntity,
                DogBreedDetails.class
        );

        DogBreedDetails dogBreedDetails=response.getBody();

        assert dogBreedDetails != null;
        DogDetails dogDetails=DogDetails.builder().weight(dogBreedDetails.getWeight().getImperial()).height(dogBreedDetails.getHeight()).breedGroup(dogBreedDetails.getBreedGroup())
                .bredFor(dogBreedDetails.getBredFor()).lifeSpan(dogBreedDetails.getLifeSpan()).name(dogBreedDetails.getName()).dogId(dogBreedDetails.getId()).build();

        testRepository.save(dogDetails);
        return dogBreedDetails;

    }

    public PlaceHolderDto placeHolder(Long id){

        String url = String.format("%s/posts/%s", placeHolderApi,id);
        String builder = UriComponentsBuilder.fromHttpUrl(url)
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);


        ResponseEntity<PlaceHolderDto> response = restTemplate.exchange(
                builder,
                HttpMethod.GET,
                httpEntity,
                PlaceHolderDto.class
        );


        return response.getBody();

    }

    public Object movieDb(Long id){

        String url = String.format("%s/%s/%s",movieDbApi,id,movieDbApiKey);
        String builder = UriComponentsBuilder.fromHttpUrl(url)
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);


        ResponseEntity<Object> response = restTemplate.exchange(
                builder,
                HttpMethod.GET,
                httpEntity,
                Object.class
        );


        return response.getBody();

    }




}
