package com.futurebytedance.primary.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/12/1 - 0:31
 * @Description synchronized可重入锁
 */
public class SyncLockDemo {
    public synchronized void add() {
        add();
    }

    public static void main(String[] args) {
        //Lock演示可重入锁
        Lock lock = new ReentrantLock();
        //创建线程
        new Thread(() -> {
            try {
                //上锁
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " 外层");
                try {
                    //上锁
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + " 内层");
                } finally {
                    lock.unlock();
                }
            } finally {
                //释放锁
                lock.unlock();
            }
        }, "t2").start();

        //创建新线程
        new Thread(() -> {
            lock.lock();
            System.out.println("aaaa");
            lock.unlock();
        }, "t3").start();

//        new SyncLockDemo().add();
        // synchronized
        Object o = new Object();
        new Thread(() -> {
            synchronized (o) {
                System.out.println(Thread.currentThread().getName() + " 外层");
                synchronized (o) {
                    System.out.println(Thread.currentThread().getName() + " 中层");
                    synchronized (o) {
                        System.out.println(Thread.currentThread().getName() + " 内层");
                    }
                }
            }
        }, "t1").start();
    }
}
