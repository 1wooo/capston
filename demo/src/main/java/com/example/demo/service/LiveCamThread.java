package com.example.demo.service;
import com.example.demo.liveStream.S3Service;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LiveCamThread extends Thread {

    S3Service s3Service;

    @Override
    public void run() {
        System.out.println("Thread start");
        while (true) {
            try {
                System.out.println("Object Download");
                s3Service.getObject();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(11000); // 2초 대기한다.
                s3Service.fileIndexSwitch();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public LiveCamThread(S3Service s3Service) {
        this.s3Service = s3Service;
    }
}
