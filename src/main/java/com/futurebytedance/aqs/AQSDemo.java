package com.futurebytedance.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/24 - 0:31
 * @Description AQS
 */
public class AQSDemo {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        lock.lock();
        try {

        } finally {
            lock.unlock();
        }

        new CountDownLatch(10);
    }
}
