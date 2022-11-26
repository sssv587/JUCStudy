package com.futurebytedance.rwlock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/26 - 17:02
 * @Description 读写锁之锁降级
 * 锁降级：遵循获取写锁→再获取读锁→再释放写锁的次序，写锁能够降级成为读锁。
 * <p>
 * 如果一个线程占有了写锁，在不释放写锁的情况下，它还能占有读锁，即写锁降级为读锁。
 * <p>
 * 读没有完成时候写锁无法获得锁，必须要等着读锁读完后才有机会写
 */
public class LockDownGradingDemo {
    public static void main(String[] args) {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

        //正常 A B两个线程
        // A
//        readLock.lock();
//        System.out.println("-----写入");
//        readLock.unlock();

        // B
//        writeLock.lock();
//        System.out.println("-----读取");
//        writeLock.unlock();


        //写锁降级成读锁
        /*
        //本例,同一个线程
        writeLock.lock();
        System.out.println("-----写入");

        //假设是一段逻辑处理。。

        readLock.lock();
        System.out.println("-----读取");

        writeLock.unlock();

        //假设是一段逻辑处理。。
        readLock.unlock();*/

        //读锁升级成写锁(不可以)
        readLock.lock();
        System.out.println("-----读取");

        writeLock.lock();
        System.out.println("-----写入");

        readLock.unlock();
        writeLock.unlock();
    }
}
