package com.futurebytedance.atomics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/20 - 22:20
 * @Description 高性能热点商品点赞计数案例
 * 需求:10个线程,每个线程100w次,总点赞数出来
 */
public class AccumulatorCompareDemo {
    public static final int _1W = 10000;
    public static final int threadNumber = 50;

    public static void main(String[] args) throws InterruptedException {
        ClickNumber clickNumber = new ClickNumber();
        long startTime;
        long endTime;

        CountDownLatch countDownLatch1 = new CountDownLatch(threadNumber);
        CountDownLatch countDownLatch2 = new CountDownLatch(threadNumber);
        CountDownLatch countDownLatch3 = new CountDownLatch(threadNumber);
        CountDownLatch countDownLatch4 = new CountDownLatch(threadNumber);

        startTime = System.currentTimeMillis();
        for (int i = 1; i <= threadNumber; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.clickBySynchronized();
                    }
                } finally {
                    countDownLatch1.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch1.await();
        endTime = System.currentTimeMillis();
        System.out.println("---costTime:" + (endTime - startTime) + " 毫秒" + "\t clickBySynchronized:" + clickNumber.number);

        startTime = System.currentTimeMillis();
        for (int i = 1; i <= threadNumber; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.clickByAtomicLong();
                    }
                } finally {
                    countDownLatch2.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch2.await();
        endTime = System.currentTimeMillis();
        System.out.println("---costTime:" + (endTime - startTime) + " 毫秒" + "\t clickByAtomicLong:" + clickNumber.atomicLong);

        startTime = System.currentTimeMillis();
        for (int i = 1; i <= threadNumber; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
                        clickNumber.clickByLongAdder();
                    }
                } finally {
                    countDownLatch3.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch3.await();
        endTime = System.currentTimeMillis();
        System.out.println("---costTime:" + (endTime - startTime) + " 毫秒" + "\t clickByLongAdder:" + clickNumber.longAdder.sum());

        startTime = System.currentTimeMillis();
        for (int i = 1; i <= threadNumber; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 100 * _1W; j++) {
//                        clickNumber.clickBySynchronized();
//                        clickNumber.clickByAtomicLong();
//                        clickNumber.clickByLongAdder();
                        clickNumber.clickByLongAccumulator();
                    }
                } finally {
//                    countDownLatch1.countDown();
//                    countDownLatch2.countDown();
//                    countDownLatch3.countDown();
                    countDownLatch4.countDown();
                }
            }, String.valueOf(i)).start();
        }
//        countDownLatch1.await();
//        countDownLatch2.await();
//        countDownLatch3.await();
        countDownLatch4.await();
        endTime = System.currentTimeMillis();
//        System.out.println("---costTime:" + (endTime - startTime) + " 毫秒" + "\t clickBySynchronized:" + clickNumber.number);
//        System.out.println("---costTime:" + (endTime - startTime) + " 毫秒" + "\t clickByAtomicLong:" + clickNumber.atomicLong);
//        System.out.println("---costTime:" + (endTime - startTime) + " 毫秒" + "\t clickByLongAdder:" + clickNumber.longAdder.sum());
        System.out.println("---costTime:" + (endTime - startTime) + " 毫秒" + "\t clickByLongAccumulator:" + clickNumber.longAccumulator.get());
    }
}

class ClickNumber {
    int number = 0;

    public synchronized void clickBySynchronized() {
        number++;
    }

    AtomicLong atomicLong = new AtomicLong(0);

    public void clickByAtomicLong() {
        atomicLong.getAndIncrement();
    }

    LongAdder longAdder = new LongAdder();

    public void clickByLongAdder() {
        longAdder.increment();
    }

    LongAccumulator longAccumulator = new LongAccumulator(Long::sum, 0);

    public void clickByLongAccumulator() {
        longAccumulator.accumulate(1);
    }
}