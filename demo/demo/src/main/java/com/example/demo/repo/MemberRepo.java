package com.example.demo.repo;

import com.example.demo.DTO.Member;

import java.util.Optional;

public interface MemberRepo {
    public Optional<Member> findByMemberId(String memberid);
}
