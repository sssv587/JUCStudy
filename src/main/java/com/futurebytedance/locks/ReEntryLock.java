package com.futurebytedance.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/8 - 23:37
 * @Description 可重入锁synchronized代码验证 与 可重入锁原理分析和lock代码验证
 * 指的是可重复可递归调用的锁,在外层使用锁之后,在内层仍然可以使用,并且不发生死锁,这样的锁就叫做可重入锁。
 * 简单的来说就是:在一个synchronized修饰的方法或代码块的内部调用本类的其他synchronized修饰的方法或代码块时,是永远可以得到锁的
 *
 * 可重入锁原理分析和lock代码验证
 * 1.lock()方法和unlock()方法需要成对出现,锁了几次,也要释放几次,否则后面的线程无法获取锁了
 * 2.unlock()方法放在finally中执行，保证不管程序是否有异常，锁必定会释放
 */
public class ReEntryLock {
    public synchronized void m1() {
        //指的是可重复可递归调用的锁,在外层使用锁之后,在内层仍然可以使用,并且不发生死锁,这样的锁就叫做可重入锁
        System.out.println(Thread.currentThread().getName() + "\t--------come in m1");
        m2();
        System.out.println(Thread.currentThread().getName() + "\t--------end m1");
    }

    public synchronized void m2() {
        System.out.println(Thread.currentThread().getName() + "\t--------come in m2");
        m3();
        System.out.println(Thread.currentThread().getName() + "\t--------end m2");
    }

    public synchronized void m3() {
        System.out.println(Thread.currentThread().getName() + "\t--------come in m3");
        System.out.println(Thread.currentThread().getName() + "\t--------end m3");
    }

    static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
//        ReEntryLock reEntryLock = new ReEntryLock();
//        new Thread(() -> {
//            reEntryLock.m1();
//        }, "t1").start();

        new Thread(() -> {
            lock.lock();

            try {
                System.out.println(Thread.currentThread().getName() + "\t-----come in外层调用");
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + "\t-----come in内层调用");
                } finally {
                    // 这里故意注释，实现加锁次数和释放次数不一样
                    // 由于加锁次数和释放次数不一样，第二个线程始终无法获取到锁，导致一直在等待。
//                    lock.unlock();// 正常情况，加锁几次就要解锁几次
                }
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t-----come in外层调用");
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }

    private static void reEntryM1() {
        final Object object = new Object();

        new Thread(() -> {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + "\t-------外层调用");
                synchronized (object) {
                    System.out.println(Thread.currentThread().getName() + "\t-------中层调用");
                    synchronized (object) {
                        System.out.println(Thread.currentThread().getName() + "\t-------内层调用");
                    }
                }
            }
        }, "t1").start();
    }
}
