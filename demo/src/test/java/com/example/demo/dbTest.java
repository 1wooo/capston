package com.example.demo;

import com.example.demo.member.Member;
import com.example.demo.repo.SpringDataJpaMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
public class dbTest {

    @Autowired
    SpringDataJpaMemberRepository springDataJpaMemberRepository;

    @Test
    @Commit
    public void Test1() {

        Member member = new Member();
        member.setName("kimwonwoo");
        member.setPassword("qwer1234");
        member.setMemberId("asdf1234");

        springDataJpaMemberRepository.save(member);

        Optional<Member> findId = springDataJpaMemberRepository.findById(member.getId());

        Assertions.assertThat(member.getName()).isEqualTo(findId.get().getName());
    }

    @Test
    @Commit
    public void Test2() {
        Member member = new Member();
        member.setName("kimwonwoo");
        member.setPassword("qwer1234");
        member.setMemberId("asdf1234");

        Optional<Member> byMemberId = springDataJpaMemberRepository.findByMemberId(member.getMemberId());

        System.out.println(byMemberId);
    }
}
