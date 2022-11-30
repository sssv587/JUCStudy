package com.futurebytedance.primary.sync;

import java.util.concurrent.TimeUnit;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/12/1 - 0:44
 * @Description 死锁
 */
public class DeadLock {
    //创建两个对象
    static final Object a = new Object();
    static final Object b = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (a) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 持有锁a,试图获取锁b");
                synchronized (b) {
                    System.out.println(Thread.currentThread().getName() + " 获取锁b");
                }
            }
        }, "A").start();

        new Thread(() -> {
            synchronized (b) {
                System.out.println(Thread.currentThread().getName() + " 持有锁b,试图获取锁a");
                synchronized (a) {
                    System.out.println(Thread.currentThread().getName() + " 获取锁a");
                }
            }
        }, "B").start();
    }
}
