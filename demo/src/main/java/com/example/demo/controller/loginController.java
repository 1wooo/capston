package com.example.demo.controller;

import com.example.demo.DTO.Member;
import com.example.demo.DTO.carNumber;
import com.example.demo.service.MemberServiceInterface;
import com.example.demo.service.SessionConst;
import com.example.demo.service.TableServiceInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@AllArgsConstructor
public class loginController {
    @Autowired
    private MemberServiceInterface memberServiceInterface;
    @Autowired
    private TableServiceInterface tableServiceInterface;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String login(@ModelAttribute LoginForm form, BindingResult bindingResult,
                        HttpServletResponse response, HttpServletRequest request, Model model) throws Exception {

        String loginId=form.getLoginId();
        String loginPassword=form.getPassword();

        if (bindingResult.hasErrors()){
            System.out.println("first Error");
            return "/";
//            System.out.println(bindingResult.getAllErrors());
        }

        // 로그인 기능 수행
        Optional<Member> loginMember = memberServiceInterface.login(loginId, loginPassword); // 여기서 널 처리
        //글로벌 에러 발생
        if(loginMember.isEmpty()){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            System.out.println("Second Error");
            return "loginPage/HomeLogin";
        }

        else {
            Member getMember = loginMember.get();
            // 성공 로직
//            Cookie cookie = new Cookie("memberId", String.valueOf(getMember.getId()));
//
//            response.addCookie(cookie);
//        return "redirect:/";

            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, getMember);

            List<Long> monthData = tableServiceInterface.getMonthData();
            model.addAttribute("monthData",monthData);
            return "mainPage/index";
        }
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getLoginPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                               Member member, HttpServletRequest request, Model model) {

        if (member == null) { return "loginPage/HomeLogin";}
        else{
            List<Long> monthData = tableServiceInterface.getMonthData();
            model.addAttribute("monthData",monthData);
            return "mainPage/index";
        }
    }

    @GetMapping("mainPage/tables")
    public String getTablePage(HttpServletRequest request, Model model){
        // 로그인 검증
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "loginPage/HomeLogin";
        }
        else{
            List<carNumber> cars = tableServiceInterface.getAll();
            model.addAttribute("illegalCars", cars);
            return "mainPage/tables";
        }
        // 로그인 검증


    }

    @GetMapping("mainPage/liveCam")
    public String getLiveCamPage(HttpServletRequest request){
        //로그인 검증
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "loginPage/HomeLogin";
        }else{
            return "mainPage/liveCam";
        }
        // 로그인 검증
        // 클라이언트에서 영상 넘겨받고 웹에 실시간으로 뿌려주는 코드 작성 필요
    }

    // Rest API로 파이썬에서 받아오는 정보들 DB등록
    @PostMapping("loginPage/testApi")
    public void ApiTest(@RequestBody HashMap<String, Object> map) throws ParseException {

        carNumber car = new carNumber();
        car.setCarN((String) map.get("carNumber"));
        car.setIllegalCode((int) map.get("illegalCode"));
        car.setFine((int) map.get("fine"));
        // 날짜처리코드
        String timeStr = (String) map.get("EnterDate");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = formatter.parse(timeStr);
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        // 날짜처리코드드

       car.setTimestamp(timestamp);

//        System.out.println(map);
        tableServiceInterface.illegalCarRegister(car);
    }


}
