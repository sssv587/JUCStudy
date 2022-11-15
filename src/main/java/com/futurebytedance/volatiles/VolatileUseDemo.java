package com.futurebytedance.volatiles;

import java.util.concurrent.TimeUnit;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/15 - 23:30
 * @Description Volatile的使用
 * 使用：作为一个布尔状态标志，用于指示发生了一个重要的一次性事件，例如完成初始化或任务结束
 * 理由：状态标志并不依赖于程序内任何其他状态，且通常只有一种状态转换
 * 例子：判断业务是否结束
 */
public class VolatileUseDemo {
    private volatile static boolean flag = true;

    public static void main(String[] args) {
        new Thread(() -> {
            while (flag) {
                //do something......
            }
        }, "t1").start();

        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            flag = false;
        }, "t2").start();
    }

    /**
     * 使用：当读远多于写，结合使用内部锁和 volatile 变量来减少同步的开销
     * 理由：利用volatile保证读取操作的可见性;利用synchronized保证复合操作的原子性
     */
    public class Counter {
        private volatile int value;

        public int getValue() {
            return value;   //利用volatile保证读取操作的可见性
        }

        public synchronized int increment() {
            return value++; //利用synchronized保证复合操作的原子性
        }
    }
}
