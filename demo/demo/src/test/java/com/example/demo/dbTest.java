package com.example.demo;
import com.example.demo.DTO.Member;
import com.example.demo.repo.CarNumberRepoInterface;
import com.example.demo.repo.SpringDataJpaMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@SpringBootTest
//@DataJpaTest
public class dbTest {

    @Autowired
    SpringDataJpaMemberRepository springDataJpaMemberRepository;
    @Autowired
    CarNumberRepoInterface carNumberRepoInterface;
//    @Autowired
//    private TestEntityManager testEntityManager;
    @Autowired
    EntityManager em;

    @Test
    @Commit
    public void test() {
        Member newMember = new Member();

        springDataJpaMemberRepository.save(newMember);
    }

}
