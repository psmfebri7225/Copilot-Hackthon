package com.myapp.model;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Column;


@Entity
@Table(name = "number_array")
public class NumberArray {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, unique = true, nullable = false)
    private UUID code = UUID.randomUUID();

    @Column(length = 5000)
    private String numberArray;

    private Integer answer;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdDate = new Date();

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getCode() {
        return code;
    }

    public void setCode(UUID code) {
        this.code = code;
    }

    public String getNumberArray() {
        return numberArray;
    }

    public void setNumberArray(String numberArray) {
        this.numberArray = numberArray;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}