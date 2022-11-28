package com.futurebytedance.primary.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/28 - 23:31
 * @Description 线程间通信-lock实现
 */
public class ThreadDemo2 {
    public static void main(String[] args) {
        Share share = new Share();

        //创建线程
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.incr();//+1
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.decr(); //-1
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.incr();//+1
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CC").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.decr(); //-1
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "DD").start();
    }
}

//第一步 创建资源类，定义属性和操作方法
class Share {
    //初始值
    private int number = 0;
    //创建Lock
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    //+1的方法
    public void incr() throws InterruptedException {
        //上锁
        lock.lock();
        try {
            //判断
            while (number != 0) {
                condition.await();
            }
            //干活
            number++;
            System.out.println(Thread.currentThread().getName() + " :: " + number);

            //通知
            condition.signalAll();
        } finally {
            //解锁
            lock.unlock();
        }
    }

    //-1的方法
    public void decr() throws InterruptedException {
        //上锁
        lock.lock();
        try {
            //判断
            while (number != 1) {
                condition.await();
            }
            //干活
            number--;
            System.out.println(Thread.currentThread().getName() + " :: " + number);

            //通知
            condition.signalAll();
        } finally {
            //解锁
            lock.unlock();
        }
    }
}