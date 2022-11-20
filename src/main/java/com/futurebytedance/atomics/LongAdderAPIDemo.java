package com.futurebytedance.atomics;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/20 - 22:11
 * @Description LongAdder类与LongAccumulator类
 * LongAdder只能用来计算加法,且从零开始计算
 * LongAccumulator提供了自定义的函数操作
 */
public class LongAdderAPIDemo {
    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();

        longAdder.increment();
        longAdder.increment();
        longAdder.increment();

        System.out.println(longAdder.sum());

        LongAccumulator longAccumulator = new LongAccumulator(Long::sum, 0);
        longAccumulator.accumulate(1);//1
        longAccumulator.accumulate(3);//4

        System.out.println(longAccumulator.get());
    }
}
