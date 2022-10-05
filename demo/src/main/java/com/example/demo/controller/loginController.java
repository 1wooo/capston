package com.example.demo.controller;

import com.example.demo.member.Member;
import com.example.demo.service.MemberServiceInterface;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@Controller
@AllArgsConstructor
public class loginController {
    @Autowired
    private MemberServiceInterface memberServiceInterface;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getLoginPage(Member member) {
        return "/loginPage/HomeLogin";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String login(@ModelAttribute LoginForm form, BindingResult bindingResult,
                        HttpServletResponse response) throws Exception {

        String loginId=form.getLoginId();
        String loginPassword=form.getPassword();
        System.out.println("id = " + loginId);
        System.out.println("pw = " + loginPassword);

        if (bindingResult.hasErrors()){
            System.out.println("first Error");
            return "/";
//            System.out.println(bindingResult.getAllErrors());
        }


        // 로그인 기능 수행
        Member loginMember = memberServiceInterface.login(loginId, loginPassword);

        //글로벌 에러 발생

        if(loginMember == null){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            System.out.println("Second Error");
            return "/loginPage/HomeLogin";
        }

        // 성공 로직
        Cookie cookie = new Cookie("memberId", String.valueOf(loginMember.getId()));

        response.addCookie(cookie);
////        return "redirect:/";

        return "/loginPage/testApi";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPageReq() {
        return "/loginPage/registerTest";
    }
}
