package com.myapp.service.impl;

import com.myapp.model.NumberArray;
import com.myapp.repository.NumberArrayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

import com.myapp.service.NumberArrayService;
import com.myapp.repository.NumberArrayRepository;
import com.myapp.model.NumberArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NumberArrayServiceImpl implements NumberArrayService {

    private final NumberArrayRepository numberArrayRepository;

    @Autowired
    public NumberArrayServiceImpl(NumberArrayRepository numberArrayRepository) {
        this.numberArrayRepository = numberArrayRepository;
    }

    @Override
    public List<NumberArray> findAll() {
        return numberArrayRepository.findAll();
    }

    @Override
    public NumberArray findById(Long id) {
        return numberArrayRepository.findById(id).orElse(null);
    }

    @Override
    public NumberArray findByCode(UUID code) {
        return numberArrayRepository.findByCode(code);
    }

    @Override
    public NumberArray save(NumberArray numberArray) {
        Map<String, Object> resultMap = generateRandomNumberArray();
        numberArray.setNumberArray((String) resultMap.get("result"));
        numberArray.setAnswer((Integer) resultMap.get("sum"));
        return numberArrayRepository.save(numberArray);
    }

    @Override
    public void deleteById(Long id) {
        numberArrayRepository.deleteById(id);
    }

    // create method to generate random number array 25 x 25, number range 0 - 9
    // create result as json string
    public Map<String, Object> generateRandomNumberArray() {
        int sum = 0;
        int[][] numberArray = new int[25][25];
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < 25; i++) {
            result.append("[");
            for (int j = 0; j < 25; j++) {
                numberArray[i][j] = (int) (Math.random() * 10);
                sum += numberArray[i][j];
                result.append(numberArray[i][j]);
                if (j < 24) {
                    result.append(",");
                }
            }
            result.append("]");
            if (i < 24) {
                result.append(",");
            }
        }
        result.append("]");


        // create map to store result and sum
        Map<String, Object> resultMap = Map.of("result", result.toString(), "sum", sum);
        return resultMap;
    }
    
}