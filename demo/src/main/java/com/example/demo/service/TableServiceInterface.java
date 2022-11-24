package com.example.demo.service;

import com.example.demo.DTO.NotificationCarNumberDTO;
import com.example.demo.DTO.carNumber;

import java.util.List;
import java.util.Optional;

public interface TableServiceInterface {
    List<carNumber> getAll();
    public void illegalCarRegister(carNumber carnumber);

    public void NotificationCarRegister(NotificationCarNumberDTO notificationCarNumberDTO);

    Boolean isExist(String carNumber);

    List<Long> getMonthData();
}
