package com.example.demo.service;

import com.example.demo.DTO.NotificationCarNumberDTO;
import com.example.demo.DTO.carNumber;
import com.example.demo.repo.CarNumberRepoInterface;
import com.example.demo.repo.NotificationCarNumberRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// 위반차량관리와 관련된 모든 서비스 구현
@Service
public class TableServiceImpl implements TableServiceInterface {

    @Autowired
    private CarNumberRepoInterface carNumberRepoInterface;

    @Autowired
    private NotificationCarNumberRepoInterface notificationCarNumberRepoInterface;

    @Override
    public List<carNumber> getAll() {
        return carNumberRepoInterface.findAll();
    }
    @Override
    public void illegalCarRegister(carNumber carnumber) {
        carNumberRepoInterface.save(carnumber);
    }

    @Override
    public void NotificationCarRegister(NotificationCarNumberDTO notificationCarNumberDTO) {
        notificationCarNumberRepoInterface.save(notificationCarNumberDTO);
    }

    @Override
    public Boolean isExist(String carNumber) {
        Optional<NotificationCarNumberDTO> findCar = notificationCarNumberRepoInterface.findBycarN(carNumber);
        return findCar.isPresent(); // 있으면 True 없으면 False
    }

    @Override
    public List<Long> getMonthData() {
        List<Long> MonthData = carNumberRepoInterface.getData();

        return MonthData;
    }
}
