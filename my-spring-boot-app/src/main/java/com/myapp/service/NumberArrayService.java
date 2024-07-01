package com.myapp.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.myapp.model.NumberArray;

public interface NumberArrayService {

    List<NumberArray> findAll();

    NumberArray findById(Long id);

    NumberArray findByCode(UUID code);

    NumberArray save(NumberArray numberArray);

    void deleteById(Long id);
}
