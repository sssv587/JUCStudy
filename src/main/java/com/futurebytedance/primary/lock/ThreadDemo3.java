package com.futurebytedance.primary.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/29 - 23:12
 * @Description 线程间定制化通信
 * AA 打印5次
 * BB 打印10次
 * CC 打印15次
 */
public class ThreadDemo3 {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    shareResource.print5(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    shareResource.print10(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    shareResource.print15(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "CC").start();
    }
}

//第一步 创建资源类
class ShareResource {
    //定义标志位
    private int flag = 1; //1 AA 2 BB 3 CC
    //创建lock锁
    private final Lock lock = new ReentrantLock();

    //创建3个condition
    private final Condition c1 = lock.newCondition();
    private final Condition c2 = lock.newCondition();
    private final Condition c3 = lock.newCondition();

    //打印5次 参数第几轮
    public void print5(int loop) throws InterruptedException {
        //上锁
        lock.lock();
        try {
            //判断
            while (flag != 1) {
                //等待
                c1.await();
            }
            //干活
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + " 轮数:" + loop);
            }
            //通知
            flag = 2; //修改标志位 2
            c2.signal(); //通知BB线程
        } finally {
            //释放锁
            lock.unlock();
        }
    }

    //打印10次 参数第几轮
    public void print10(int loop) throws InterruptedException {
        //上锁
        lock.lock();
        try {
            //判断
            while (flag != 2) {
                //等待
                c2.await();
            }
            //干活
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + " 轮数:" + loop);
            }
            //通知
            flag = 3; //修改标志位 2
            c3.signal(); //通知CC线程
        } finally {
            //释放锁
            lock.unlock();
        }
    }

    //打印15次 参数第几轮
    public void print15(int loop) throws InterruptedException {
        //上锁
        lock.lock();
        try {
            //判断
            while (flag != 3) {
                //等待
                c3.await();
            }
            //干活
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + " 轮数:" + loop);
            }
            //通知
            flag = 1; //修改标志位 2
            c1.signal(); //通知AA线程
        } finally {
            //释放锁
            lock.unlock();
        }
    }
}