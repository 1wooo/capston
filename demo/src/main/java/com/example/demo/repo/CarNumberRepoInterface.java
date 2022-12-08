package com.example.demo.repo;

import com.example.demo.DTO.illegarCarNumberDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarNumberRepoInterface extends JpaRepository<illegarCarNumberDTO, Long>, MonthChartRepoInterface {
}
