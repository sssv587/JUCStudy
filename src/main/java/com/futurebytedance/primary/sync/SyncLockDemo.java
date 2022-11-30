package com.futurebytedance.primary.sync;

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
        new SyncLockDemo().add();
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
