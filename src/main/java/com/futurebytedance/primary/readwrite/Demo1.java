package com.futurebytedance.primary.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/12/2 - 0:55
 * @Description 读写锁的降级
 */
public class Demo1 {
    public static void main(String[] args) {
        //可重入读写锁对象
        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();

        //锁降级
        //1.获取写锁
        writeLock.lock();
        System.out.println("w");

        //2.获取读锁
        readLock.lock();
        System.out.println("r");

        //3.释放写锁
        writeLock.unlock();

        //4.释放读锁
        readLock.unlock();
    }
}
