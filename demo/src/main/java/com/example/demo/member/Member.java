package com.example.demo.member;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Member {

    private String name;
    private String id;

    public Member(){}
    public Member(String name, String id) {
        this.name = name;
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}