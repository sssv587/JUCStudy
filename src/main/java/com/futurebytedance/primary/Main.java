package com.futurebytedance.primary;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/28 - 22:00
 * @Description 守护线程
 */
public class Main {
    public static void main(String[] args) {
        Thread aaa = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "::" + Thread.currentThread().isDaemon());
//            while (true) {
//
//            }
        }, "aaa");

        //设置守护线程
//        aaa.setDaemon(true);
        aaa.start();

        System.out.println(Thread.currentThread().getName() + " over");
    }
}
