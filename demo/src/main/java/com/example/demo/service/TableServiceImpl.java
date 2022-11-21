package com.example.demo.service;

import com.example.demo.DTO.carNumber;
import com.example.demo.repo.CarNumberRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 위반차량관리와 관련된 모든 서비스 구현
@Service
public class TableServiceImpl implements TableServiceInterface {

    @Autowired
    private CarNumberRepoInterface carNumberRepoInterface;

    @Override
    public List<carNumber> getAll() {
        return carNumberRepoInterface.findAll();
    }
    @Override
    public void illegalCarRegister(carNumber carnumber) {
        carNumberRepoInterface.save(carnumber);
    }

    @Override
    public List<Long> getMonthData() {
        List<Long> MonthData = carNumberRepoInterface.getData();

        return MonthData;
    }
}
