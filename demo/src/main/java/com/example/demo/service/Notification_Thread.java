package com.example.demo.service;

import com.example.demo.DTO.MessageDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Notification_Thread extends Thread {
    private TableServiceInterface tableServiceInterface;
    private SmsService smsService;
    private int sec;
    private String carNumber;
    public Notification_Thread(SmsService smsService, TableServiceInterface tableServiceInterface, String carNumber) {
        this.sec = 0;
        this.tableServiceInterface = tableServiceInterface;
        this.smsService = smsService;
        this.carNumber = carNumber;
    }
    public void run() {
        MessageDTO sendMsg = new MessageDTO();
//        System.out.println(this.seq + " thread start.");  // 쓰레드 시작
        while (true) {
//            if () 스레드가 돌아가면서도 차량번호에 전화번호가 매칭돼있는지 체크해야함.
            try {
                Thread.sleep(1000);  // 1초 대기한다.
            } catch (Exception e) {
            }
            sec += 1;
            if (sec == 60){
                String phoneN = tableServiceInterface.isExistPhoneNumber(carNumber);
                if (phoneN != null){
                    sendMsg.setContent("주차시간 30분 소요되었습니다.");
                    sendMsg.setTo(phoneN);
                    try {
                        smsService.sendSms(sendMsg);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidKeyException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                }

            } else if (sec == 90) {
                String phoneN = tableServiceInterface.isExistPhoneNumber(carNumber);
                if (phoneN != null){
                    sendMsg.setContent("주차시간 60분 소요되었습니다.");
                    sendMsg.setTo(phoneN);
                    try {
                        smsService.sendSms(sendMsg);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidKeyException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                }
                // 1시간 알림
            } else if (sec == 120) {
                String phoneN = tableServiceInterface.isExistPhoneNumber(carNumber);
                if (phoneN != null){
                    sendMsg.setContent("허용 주차시간이 종료되었습니다. 출차해주시기 바랍니다.");
                    sendMsg.setTo(phoneN);
                    try {
                        smsService.sendSms(sendMsg);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidKeyException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                }
                // 시간 종료 알림
                break;
            }
        }

//        System.out.println(this.seq + " thread end.");  // 쓰레드 종료
    }
}
