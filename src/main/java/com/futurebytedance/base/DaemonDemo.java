package com.futurebytedance.base;

import java.util.concurrent.*;

public class DaemonDemo {
    public static void main(String[] args) {//一切方法运行的入口
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 开始运行" +
                    (Thread.currentThread().isDaemon() ? "守护线程" : "用户线程"));
            while (true) {
            }
        }, "t1");

        //setDaemon()必须在start()方法之前执行,因为一旦start,就是用户线程
        t1.setDaemon(true);
        t1.start();

        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "\t---- 主线程");
    }
}
