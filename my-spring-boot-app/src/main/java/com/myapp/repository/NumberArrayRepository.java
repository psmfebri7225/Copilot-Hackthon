package com.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.myapp.model.NumberArray;

@Repository
public interface NumberArrayRepository extends JpaRepository<NumberArray, Long> {

    List<NumberArray> findAll();

    // add custom query to get NumberArray by code
    NumberArray findByCode(UUID code);
}
