package com.futurebytedance.rwlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/26 - 17:38
 * @Description 邮戳锁(StampedLock)
 * StampedLock = ReentrantReadWriteLock + 读的过程中也允许获取写锁介入
 */
public class StampedLockDemo {
    static int number = 37;
    static StampedLock stampedLock = new StampedLock();

    public void write() {
        //返回戳记
        long stamp = stampedLock.writeLock();
        System.out.println(Thread.currentThread().getName() + "\t" + "写线程准备修改");
        try {
            number = number + 13;
        } finally {
            stampedLock.unlockWrite(stamp);
        }
        System.out.println(Thread.currentThread().getName() + "\t" + "写线程结束修改");
    }

    //悲观读,读没有完成的时候写锁无法获得
    public void read() {
        long stamp = stampedLock.readLock();
        System.out.println(Thread.currentThread().getName() + "\t" + "come in readLock code block,4 seconds continue...");
        for (int i = 1; i <= 4; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "正在读取中....");
        }
        try {
            int result = number;
            System.out.println(Thread.currentThread().getName() + "\t" + "获得成员变量值result:" + result);
            System.out.println("写线程没有修改成功,读锁时候写锁无法介入,传统的读写互斥");
        } finally {
            stampedLock.unlockRead(stamp);
        }
    }

    public static void main(String[] args) {
        StampedLockDemo resource = new StampedLockDemo();
        new Thread(() -> {
            resource.read();
        }, "readThread").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "-----come in");
            resource.write();
        }, "writeThread").start();

        try {TimeUnit.MILLISECONDS.sleep(4000);} catch (Exception e) {e.printStackTrace();}

        System.out.println(Thread.currentThread().getName() + "\t" + "number:" + number);
    }
}
