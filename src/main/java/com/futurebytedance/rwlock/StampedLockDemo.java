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

    //传统的写方法
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

    //传统的读方法
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

    //乐观读,读的过程也允许获取写锁介入
    public void tryOptimisticRead() {
        long stamp = stampedLock.tryOptimisticRead();
        int result = number;
        //故意间隔4秒钟，很乐观认为读取中没有其它线程修改过number值，具体靠判断
        System.out.println("4秒前stampedLock.validate方法值(true无修改，false有修改)" + "\t" + stampedLock.validate(stamp));
        for (int i = 0; i < 4; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "正在读取... " + (i + 1) + " 秒" +
                    "后stampedLock.validate方法值(true无修改，false有修改)" + "\t" + stampedLock.validate(stamp));
        }
        if (!stampedLock.validate(stamp)) {
            System.out.println("有人修改过------有写操作");
            stamp = stampedLock.readLock();
            try {
                System.out.println("从乐观读 升级为 悲观读");
                result = number;
                System.out.println("重新悲观读后result：" + result);
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }
        System.out.println(Thread.currentThread().getName() + "\t" + " finally value: " + result);
    }

    public static void main(String[] args) {
        StampedLockDemo resource = new StampedLockDemo();

        //传统版读写锁
        /*
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

        try {
            TimeUnit.MILLISECONDS.sleep(4000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "\t" + "number:" + number);*/

        new Thread(() -> {
            resource.tryOptimisticRead();
        }, "readThread").start();

        //暂停2s线程,读读过程可以写介入,演示
//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //暂停2s线程,没有写线程介入
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "-----come in");
            resource.write();
        }, "writeThread").start();
    }
}
