package com.example.demo.repo;

import com.example.demo.member.Member;

import java.util.List;
import java.util.Optional;

public class SQLimpl implements userRepointerface {


    @Override
    public Member save() {
        return null;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findByName(String Name) {
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        return null;
    }
}
