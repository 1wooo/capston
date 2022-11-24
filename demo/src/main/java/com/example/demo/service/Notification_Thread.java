package com.example.demo.service;

public class Notification_Thread extends Thread {
    private TableServiceInterface tableServiceInterface;
    private int sec;
    public Notification_Thread(TableServiceInterface tableServiceInterface) {
        this.sec = 0;
        this.tableServiceInterface = tableServiceInterface;
    }
    public void run() {
//        System.out.println(this.seq + " thread start.");  // 쓰레드 시작
        while (true) {
//            if () 스레드가 돌아가면서도 차량번호에 전화번호가 매칭돼있는지 체크해야함.
            try {
                Thread.sleep(1000);  // 1초 대기한다.
            } catch (Exception e) {
            }
            sec += 1;
            if (sec == 1800){
                // 30분 알림
            } else if (sec == 3600) {
                // 1시간 알림
            } else if (sec == 4200) {
                // 시간 종료 알림
                break;
            }
        }

//        System.out.println(this.seq + " thread end.");  // 쓰레드 종료
    }
}
