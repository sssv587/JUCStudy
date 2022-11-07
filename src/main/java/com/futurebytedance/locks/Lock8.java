package com.futurebytedance.locks;

import java.util.concurrent.TimeUnit;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/7 - 23:29
 * @Description 8锁案例说明
 * 题目：谈谈你对多线程锁的理解,8锁案例说明
 * 口诀：线程   操作  资源类
 * 8锁案例说明：
 * 1 标准访问有ab两个线程，请问先打印邮件还是短信                                邮件
 * 2 sendEmail方法中加入暂停3秒钟，请问先打印邮件还是短信                        邮件
 * 3 添加一个普通的hello方法，请问先打印邮件还是hello                            hello
 * 4 有两部手机，请问先打印邮件还是短信                                         短信
 * 5 有两个静态同步方法，有1部手机，请问先打印邮件还是短信                        邮件
 * 6 有两个静态同步方法，有2部手机，请问先打印邮件还是短信                        邮件
 * 7 有1个静态同步方法，有1个普通同步方法,有1部手机，请问先打印邮件还是短信        短信
 * 8 有1个静态同步方法，有1个普通同步方法,有2部手机，请问先打印邮件还是短信        短信
 */
public class Lock8 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        Phone phone2 = new Phone();

//        new Thread(phone::sendEmail, "a").start();
        new Thread(() -> phone.sendEmail(), "a").start();

        //保证a线程先启动
        try {TimeUnit.MILLISECONDS.sleep(500);} catch (Exception e) {e.printStackTrace();}

//        new Thread(phone::sendSMS, "b").start();
//        new Thread(phone::hello, "b").start();
//        new Thread(phone2::sendSMS, "b").start();
//        new Thread(() -> phone.sendSMS(), "b").start();
//        new Thread(() -> phone2.sendSMS(), "b").start();
//        new Thread(() -> phone.sendSMS(), "b").start();
        new Thread(() -> phone2.sendSMS(), "b").start();
    }
}

class Phone {
//    public synchronized void sendEmail() {
//        try {TimeUnit.MILLISECONDS.sleep(3000);} catch (Exception e) {e.printStackTrace();}
//        System.out.println("--------------sendEmail");
//    }
//
//    public synchronized void sendSMS() {
//        System.out.println("--------------sendSMS");
//    }

    public static synchronized void sendEmail() {
        try {TimeUnit.MILLISECONDS.sleep(3000);} catch (Exception e) {e.printStackTrace();}
        System.out.println("--------------sendEmail");
    }

//    public static synchronized void sendSMS() {
//        System.out.println("--------------sendSMS");
//    }

    public synchronized void sendSMS() {
        System.out.println("--------------sendSMS");
    }

    public void hello(){
        System.out.println("--------------hello");
    }
}