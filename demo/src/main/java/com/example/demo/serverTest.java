package com.example.demo;

import com.example.demo.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller // controller임을 알려주는 표시
@RequestMapping("/api") // 이곳으로 들어오는 API주소를 mapping, /api주소로 받겠다(localhost:8080/api)
public class serverTest {

    @PostMapping("/testApi")
    public String test(@RequestBody Member member) {

        System.out.println("name = " + member.getName());
        System.out.println("id = " + member.getId());

        return member.getName();
    }


}
