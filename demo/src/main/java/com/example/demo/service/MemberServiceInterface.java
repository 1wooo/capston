package com.example.demo.service;

import com.example.demo.member.Member;

public interface MemberServiceInterface {

    public void register(Member member) throws Exception;

    public Member login(String MemberId, String MemberPassword) throws Exception;
}
