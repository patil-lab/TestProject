package com.example.testproject.service;

import com.example.testproject.entity.DogDetails;
import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<DogDetails,Long> {
}
