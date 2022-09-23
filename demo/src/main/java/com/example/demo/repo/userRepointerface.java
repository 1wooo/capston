package com.example.demo.repo;

import com.example.demo.member.Member;

import java.util.List;
import java.util.Optional;

public interface userRepointerface {

    Member save();

    Optional<Member> findById(Long id);
    Optional<Member> findByName(String Name);

    List<Member> findAll();

}
