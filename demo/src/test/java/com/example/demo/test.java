package com.example.demo;

import com.example.demo.DTO.MessageDTO;
import com.example.demo.DTO.NotificationCarNumberDTO;
import com.example.demo.repo.NotificationCarNumberRepoInterface;
import com.example.demo.s3.S3Service;
import com.example.demo.service.Notification_Thread;
import com.example.demo.service.SmsService;
import com.example.demo.service.TableServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

//@SpringBootTest
public class test {
//
//    @Autowired
//    SmsService smsService;
//    @Autowired
//    TableServiceInterface tableServiceInterface;
//    @Autowired
//    S3Service s3Service;

//    @Test
//    public void test() {
//        List<String> s3Keys = s3Service.getS3Keys();
//        List<String> url = s3Service.objectsURL(s3Keys);
//
//        System.out.println(url);
//    }

    @Test
    public void te() {

        Timestamp enterTime = Timestamp.valueOf("2022-11-30 19:01:01");
        Timestamp exitTime = Timestamp.valueOf("2022-11-30 20:01:55");

        Calendar enTime = Calendar.getInstance();
        Calendar exTime = Calendar.getInstance();

        enTime.setTime(enterTime);
        exTime.setTime(exitTime);

        int diffHour = exTime.get(Calendar.HOUR) - enTime.get(Calendar.HOUR);
        int diffMin = exTime.get(Calendar.MINUTE) - enTime.get(Calendar.MINUTE);

        System.out.println(diffMin);
        System.out.println(diffHour);
    }
//    @Test
//    @Commit
//    public void updatePhoneNumberTest() {
//
//        tableServiceInterface.updatePhoneNumber("12주1233", "01098442244");
//
//    }
//
//    @Test
//    @Transactional
//    public void test3() {
//        String existPhoneNumber = tableServiceInterface.isExistPhoneNumber("12주1233");
//        String ex2 = tableServiceInterface.isExistPhoneNumber("73더9872");
//        Assertions.assertThat(existPhoneNumber).isEqualTo("01041418835");
//        Assertions.assertThat(ex2).isEqualTo(null);
//
//    }
//
//    @Test
//    public void SmsTest() throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
//        MessageDTO testSMS = new MessageDTO();
//
//        testSMS.setTo("01088178254");
//        testSMS.setContent("ㅋㅋ엄준식 API Test ㅋㅋ ㅋㅋ ㅋㅋ ㅋㅋ ㅋㅋ 엄준식~~~~~~~");
//
//        smsService.sendSms(testSMS);
//    }
}
