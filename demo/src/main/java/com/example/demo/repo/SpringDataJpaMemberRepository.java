package com.example.demo.repo;

import com.example.demo.DTO.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepo {
}
