package com.example.testproject.controller;

import com.example.testproject.dto.client.DogBreedDetails;
import com.example.testproject.dto.client.DogBreedDto;
import com.example.testproject.dto.client.PlaceHolderDto;
import com.example.testproject.dto.client.TestServiceResult;
import com.example.testproject.repository.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${api-version}/dog", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {

    private TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping(value = "/breeds")
    public ResponseEntity<TestServiceResult<List<DogBreedDto>>> dogBreeds(@RequestParam("limit") int limit, @RequestParam("page") int page){

        List<DogBreedDto> dogBreedDto=testService.getDogBreeds(limit,page);
        return ResponseEntity.ok(TestServiceResult.succeed(dogBreedDto));
    }

    @GetMapping(value = "/breeds/{breed_id}")
    public ResponseEntity<TestServiceResult<DogBreedDetails>> dogBreeds(@PathVariable("breed_id") String breedId){

        DogBreedDetails dogBreedDetails = testService.getDogBreedDetails(breedId);

        return ResponseEntity.ok(TestServiceResult.succeed(dogBreedDetails));
    }

    @GetMapping(value = "/placeholder/{id}")
    public ResponseEntity<TestServiceResult<PlaceHolderDto>> placeHolderApi(@PathVariable Long id){
        PlaceHolderDto placeHolderDto=testService.placeHolder(id);

        return ResponseEntity.ok(TestServiceResult.succeed(placeHolderDto));
    }

    @GetMapping(value = "/movie/{id}")
    public ResponseEntity<TestServiceResult<Object>> movieDb(@PathVariable Long id){
        Object object=testService.movieDb(id);

        return ResponseEntity.ok(TestServiceResult.succeed(object));
    }
}
