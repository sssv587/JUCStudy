package com.futurebytedance.atomics;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/20 - 17:28
 * @Description 数组类型原子类
 */
public class AtomicIntegerArrayDemo {
    public static void main(String[] args) {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[5]);
        //AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(5);
        //AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[]{1,2,3,4,5});

        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            System.out.println(atomicIntegerArray.get(i));
        }
        System.out.println();
        System.out.println();
        System.out.println();

        int tmpInt = 0;

        tmpInt = atomicIntegerArray.getAndSet(0, 1122);
        System.out.println(tmpInt + "\t" + atomicIntegerArray.get(0));

        atomicIntegerArray.getAndIncrement(1);
        atomicIntegerArray.getAndIncrement(1);

        tmpInt = atomicIntegerArray.getAndIncrement(1);
        System.out.println(tmpInt + "\t" + atomicIntegerArray.get(1));
    }
}
