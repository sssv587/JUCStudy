package com.futurebytedance.primary.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/28 - 22:26
 * @Description Lock接口实现卖票案例
 */
public class LSaleTicket {
    public static void main(String[] args) {
        LTicket lTicket = new LTicket();

        //创建三个线程
        new Thread(() -> {
            //调用卖票方法
            for (int i = 0; i < 40; i++) {
                lTicket.sale();
            }
        }, "AA").start();

        new Thread(() -> {
            //调用卖票方法
            for (int i = 0; i < 40; i++) {
                lTicket.sale();
            }
        }, "BB").start();

        new Thread(() -> {
            //调用卖票方法
            for (int i = 0; i < 40; i++) {
                lTicket.sale();
            }
        }, "CC").start();
    }
}

//第一步 创建资源类，定义属性和操作方法
class LTicket {
    //票数
    private int number = 30;

    //创建可重入锁
    ReentrantLock lock = new ReentrantLock();

    //操作方法：卖票
    public void sale() {
        //上锁
        lock.lock();
        try {
            //判断是否有票
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + ":卖出" + (number--) + " 剩余:" + number);
            }
        } finally {
            lock.unlock();
        }
    }
}