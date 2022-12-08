package com.example.demo.controller;

import com.example.demo.DTO.Member;
import com.example.demo.DTO.NotificationCarNumberDTO;
import com.example.demo.DTO.illegarCarNumberDTO;
import com.example.demo.s3.S3Service;
import com.example.demo.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
    @Autowired
    private SmsService smsService;

    @Autowired
    private S3Service s3Service;


    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String login(@ModelAttribute LoginForm form, BindingResult bindingResult,
                        HttpServletRequest request, Model model) throws Exception {

        String loginId = form.getLoginId();
        String loginPassword = form.getPassword();

        if (bindingResult.hasErrors()) {
            System.out.println("first Error");
            return "/";
//            System.out.println(bindingResult.getAllErrors());
        }

        // 로그인 기능 수행
        Optional<Member> loginMember = memberServiceInterface.login(loginId, loginPassword); // 여기서 널 처리
        //글로벌 에러 발생
        if (loginMember.isEmpty()) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            System.out.println("Second Error");
            return "loginPage/HomeLogin";
        } else {
            Member getMember = loginMember.get();
            // 성공 로직
//            Cookie cookie = new Cookie("memberId", String.valueOf(getMember.getId()));
//
//            response.addCookie(cookie);
//        return "redirect:/";

            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, getMember);

            List<Long> monthData = tableServiceInterface.getMonthData();
            model.addAttribute("monthData", monthData);

            return "mainPage/index";
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getLoginPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                               Member member, HttpServletRequest request, Model model) {

        if (member == null) {
            return "loginPage/HomeLogin";
        } else {
            List<Long> monthData = tableServiceInterface.getMonthData();
            model.addAttribute("monthData", monthData);
            return "mainPage/index";
        }
    }

    @GetMapping("mainPage/tables")
    public String getTablePage(HttpServletRequest request, Model model) {
        // 로그인 검증
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "loginPage/HomeLogin";
        } else {
            List<illegarCarNumberDTO> cars = tableServiceInterface.getAll();
            model.addAttribute("illegalCars", cars);
            return "mainPage/tables";
        }
        // 로그인 검증
    }

    @GetMapping("mainPage/Carlog")
    public String getCarlogPage(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "loginPage/HomeLogin";
        } else {
            List<String> keys = s3Service.getS3Keys();
            List<String> urls = s3Service.objectsURL(keys);
            model.addAttribute("urls", urls);
            return "mainPage/Carlog";
        }
    }

    @GetMapping("mainPage/liveCam")
    public String getLiveCamPage(HttpServletRequest request, Model model) {

        //로그인 검증
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "loginPage/HomeLogin";
        } else {
            return "mainPage/liveCam";
        }
        // 로그인 검증
        // 클라이언트에서 영상 넘겨받고 웹에 실시간으로 뿌려주는 코드 작성 필요
    }

//    @PostMapping("liveCamIndex/receive")
//    public void indexReceive(@RequestBody HashMap<String, Object> map) {
//        s3Service.setFileIndex(Integer.parseInt((String) map.get("camIndex")));
//    }

    // Rest API로 파이썬에서 받아오는 정보들 DB등록
    @PostMapping("loginPage/testApi")
    public void ApiTest(@RequestBody HashMap<String, Object> map) throws ParseException {

        illegarCarNumberDTO car = new illegarCarNumberDTO();
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

    @PostMapping("notification/exittimeupdate")
    public void carUpdateExitTime(@RequestBody HashMap<String, Object> map) throws ParseException {
        String timeStr = (String) map.get("Exitdate");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = formatter.parse(timeStr);
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());

        tableServiceInterface.updateCurrentCarExitTime((String) map.get("carNumber"), timestamp);
    }

    @PostMapping("notification/notificationRegister")
    public void NotificationCarRegister(@RequestBody HashMap<String, Object> map) throws ParseException {
        NotificationCarNumberDTO notificationCarNumberDTO = new NotificationCarNumberDTO();
        notificationCarNumberDTO.setCarN((String) map.get("carNumber"));
        //시간 계산용 스레드
        Notification_Thread notification_thread = new Notification_Thread(smsService, tableServiceInterface, (String) map.get("carNumber"));
        notification_thread.start();
        //시간 계산용 스레드

        // 날짜처리코드
        String timeStr = (String) map.get("EnterDate");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = formatter.parse(timeStr);
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        // 날짜처리코드
        notificationCarNumberDTO.setTimestamp(timestamp);

        if (!tableServiceInterface.isExist((String) map.get("carNumber")).isPresent()) {
            // 알림 서비스 등록 안된 차량이면 일단 db에 전화번호 없이 등록
            tableServiceInterface.NotificationCarRegister(notificationCarNumberDTO);
        } else {
            tableServiceInterface.updateEnteringTime((String)map.get("carNumber"), timestamp);
            tableServiceInterface.resetNewCarExitTime((String)map.get("carNumber"));
        }
    }

    @PostMapping("mainPage/tables/carRemove")
    public String illegalCarNumberRemove(@RequestParam List<String> illegalCarNumberTableID) {

        for (int i = 0; i < illegalCarNumberTableID.size(); i++) {
            Long id = Long.valueOf(illegalCarNumberTableID.get(i));
            tableServiceInterface.illegalCarRemove(id);
        }
        return "redirect:/mainPage/tables";
    }

    @GetMapping("notification/notificationService")
    public String getNotificationServiceWeb(@SessionAttribute(name = NotificationSessionConst.NOTIFY_CAR, required = false)
                                                NotificationCarNumberDTO notificationCarNumberDTO) {
        if (notificationCarNumberDTO == null) {
            return "notification/notificationService";
        }

        return "notification/notificationService";
    }

    @PostMapping("notification/notificationService")
    public String notificationServiceRegister(@ModelAttribute NotificationCarNumberDTO notificationCarNumberDTO,
                                              Model model, HttpServletRequest request) {

        String inputCarNumber = notificationCarNumberDTO.getCarN();
        String inputPhoneNumber = notificationCarNumberDTO.getPhoneNumber();

        if (!tableServiceInterface.isExist(inputCarNumber).isPresent()) {
            model.addAttribute("msg", "해당번호로 입차한 차량이 없습니다.");
            model.addAttribute("url", "notificationService");
            return "notification/messageRedirect";
        }
        if (tableServiceInterface.isExistPhoneNumber(inputCarNumber) == null) {
            tableServiceInterface.updatePhoneNumber(inputCarNumber, inputPhoneNumber);
            model.addAttribute("msg", "주차 알림 서비스 신규등록이 완료되었습니다.");
            model.addAttribute("url", "current");
        } else {
            model.addAttribute("msg", "등록번호 확인 완료!");
            model.addAttribute("url", "current");
        }

        HttpSession session = request.getSession();
        session.setAttribute(NotificationSessionConst.NOTIFY_CAR, tableServiceInterface.isExist(inputCarNumber).get());

        return "notification/messageRedirect";
    }

    @GetMapping("notification/current")
    public String notificationCurrentInfo(@SessionAttribute(name = NotificationSessionConst.NOTIFY_CAR, required = false)
                                              NotificationCarNumberDTO notificationCarNumberDTO,
                                          Model model) {
        if (notificationCarNumberDTO == null) {
            return "notification/notificationService";
        }
        Optional<NotificationCarNumberDTO> DTOforGetEnteringTime = tableServiceInterface.isExist(notificationCarNumberDTO.getCarN());
        model.addAttribute("enteringTime", DTOforGetEnteringTime.get().getTimestamp());

        return "notification/current";
    }

    @GetMapping("/webcamTest")
    public String download() throws IOException {

        return "mainPage/webcamTest";
    }


}

