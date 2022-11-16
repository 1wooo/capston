package com.example.demo;
import com.example.demo.repo.CarNumberRepoInterface;
import com.example.demo.repo.SpringDataJpaMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
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
    @Transactional
    public void test() {
        class month{
            Long cnt;
        }
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        String formatedNow = now.format(formatter);

        List<Long> MonthStatistics = new ArrayList<>();
        for (int i=1; i<=12; i++){
            String stDate = '\'' + formatedNow + '-' + i + '-' + "01" + '\'';

            Optional<Long> any = em.createQuery(
                            "SELECT count(c.id) FROM carNumber c WHERE MONTH("+stDate+") = MONTH(c.timestamp)"
                            , Long.class)
                    .getResultList()
                    .stream().findAny();
            MonthStatistics.add(any.get());
        }
        System.out.println(MonthStatistics);
    }

}
