package com.futurebytedance.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/19 - 22:46
 * @Description CAS
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        System.out.println(atomicInteger.compareAndSet(5, 2022) + "\t" + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 2022) + "\t" + atomicInteger.get());

        System.out.println(atomicInteger.getAndIncrement());
    }
}
