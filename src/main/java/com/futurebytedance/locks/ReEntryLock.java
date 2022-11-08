package com.futurebytedance.locks;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/8 - 23:37
 * @Description 可重入锁synchronized代码验证
 * 指的是可重复可递归调用的锁,在外层使用锁之后,在内层仍然可以使用,并且不发生死锁,这样的锁就叫做可重入锁。
 * 简单的来说就是:在一个synchronized修饰的方法或代码块的内部调用本类的其他synchronized修饰的方法或代码块时,是永远可以得到锁的
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

    public static void main(String[] args) {
        ReEntryLock reEntryLock = new ReEntryLock();
        new Thread(() -> {
            reEntryLock.m1();
        }, "t1").start();
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
