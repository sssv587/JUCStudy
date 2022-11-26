package com.futurebytedance.rwlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/26 - 16:33
 * @Description 锁演化(读写锁)
 */
public class ReentrantReadWriteLockDemo {
    public static void main(String[] args) {
        MyResource myResource = new MyResource();

        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.write(finalI + "", finalI + "");
            }, String.valueOf(i)).start();
        }

        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.read(finalI + "");
            }, String.valueOf(i)).start();
        }

        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 1; i <= 3; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.write(finalI + "", finalI + "");
            }, "新写锁线程--->" +i).start();
        }
    }
}

class MyResource //资源类，模拟一个简单的缓存
{
    Map<String, String> map = new HashMap<>();
    //=====ReentrantLock 等价于 =====synchronized，之前讲解过
    Lock lock = new ReentrantLock();
    //=====ReentrantReadWriteLock 一体两面，读写互斥，读读共享
    ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void write(String key, String value) {
        rwLock.writeLock().lock();
//        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + "正在写入");
            map.put(key, value);
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "完成写入");
        } finally {
//            lock.unlock();
            rwLock.writeLock().unlock();
        }
    }

    public void read(String key) {
//        lock.lock();
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + "正在读取");
            String result = map.get(key);
            //暂停200ms
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //暂停2000ms,演示读锁没有完成之前,写锁无法获得
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "完成读取  " + result);
        } finally {
//            lock.unlock();
            rwLock.readLock().unlock();
        }
    }
}