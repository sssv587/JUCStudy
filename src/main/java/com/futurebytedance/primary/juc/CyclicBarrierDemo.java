package com.futurebytedance.primary.juc;

import java.util.concurrent.CyclicBarrier;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/12/1 - 23:07
 * @Description CyclicBarrier
 * CyclicBarrier 看英文单词可以看出大概就是循环阻塞的意思，在使用中 CyclicBarrier 的构造方法第一个参数是目标障碍数，
 * 每次执行 CyclicBarrier 一次障碍数会加一，如果达到了目标障碍数，才会执行 cyclicBarrier.await()之后
 * 的语句。可以将 CyclicBarrier 理解为加 1 操作
 */
public class CyclicBarrierDemo {
    //集齐7颗龙珠就可以召唤神龙

    //创建固定值
    private static final int NUMBER = 7;

    public static void main(String[] args) {
        //创建CyclicBarrier
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("集齐7颗龙珠就可以召唤神龙");
        });

        //集齐七颗龙珠过程
        for (int i = 1; i <= 7; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 星龙被收集到了");
                    //等待
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
