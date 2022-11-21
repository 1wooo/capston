package com.example.demo.service;

import com.example.demo.DTO.carNumber;

import java.util.List;

public interface TableServiceInterface {
    List<carNumber> getAll();
    public void illegalCarRegister(carNumber carnumber);

    List<Long> getMonthData();
}
