package com.example.demo.service;

import com.example.demo.member.Member;
import com.example.demo.repo.SpringDataJpaMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberServiceInterface {

    @Autowired
    private SpringDataJpaMemberRepository springDataJpaMemberRepository;

    @Override
    public void register(Member member) throws Exception {
        springDataJpaMemberRepository.save(member);
    }


    @Override
    public Member login(String MemberId, String MemberPassword) throws Exception {

        Optional<Member> findMember = springDataJpaMemberRepository.findByMemberId(MemberId);
        System.out.println(findMember);
        Member returnMember = findMember.get(); // null인 경우에 대해 예외처리 리팩토링 필요

        return returnMember;
    }
}
