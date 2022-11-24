package com.example.demo;

import com.example.demo.DTO.NotificationCarNumberDTO;
import com.example.demo.repo.NotificationCarNumberRepoInterface;
import com.example.demo.service.TableServiceInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class test {

    @Autowired
    private TableServiceInterface tableServiceInterface;
    @Autowired
    private NotificationCarNumberRepoInterface notificationCarNumberRepoInterface;

//    @Test
//    @Transactional
//    public void test1() throws ParseException {
//        NotificationCarNumberDTO tmp = new NotificationCarNumberDTO();
//
//        tmp.setCarN("12주1233");
//
//        String timeStr = "2022-11-23 19:23:23";
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        Date date = formatter.parse(timeStr);
//        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
//
//        tmp.setTimestamp(timestamp);
//
//        tableServiceInterface.NotificationCarRegister(tmp);
//    }

    @Test
    @Transactional
    public void test2() {
        NotificationCarNumberDTO notificationCarNumberDTO1 = new NotificationCarNumberDTO();
        notificationCarNumberDTO1.setCarN("12주1233");
        Boolean tmp = tableServiceInterface.isExist(notificationCarNumberDTO1.getCarN());
        System.out.println(tmp);

        NotificationCarNumberDTO notificationCarNumberDTO2 = new NotificationCarNumberDTO();
        notificationCarNumberDTO2.setCarN("20하9999");
        Boolean tmp1 = tableServiceInterface.isExist(notificationCarNumberDTO2.getCarN());
        System.out.println(tmp1);

    }


}
