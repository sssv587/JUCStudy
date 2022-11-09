package com.futurebytedance.interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/9 - 23:28
 * @Description 如何停止中断运行中的线程?
 *  1.通过一个volatile变量实现
 *  2.通过AtomicBoolean
 *
 */
public class InterruptDemo {
    static volatile boolean isStop = false;
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {
//        m1_volatile();

//        m2_atomicBoolean();
    }

    //2.通过AtomicBoolean
    private static void m2_atomicBoolean() {
        new Thread(() -> {
            while (true) {
                if (atomicBoolean.get()) {
                    System.out.println(Thread.currentThread().getName() + "\t atomicBoolean被修改为true,程序停止");
                    break;
                }
                System.out.println("t1 .....hello atomicBoolean");
            }
        }, "t1").start();

        //暂停毫秒
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            atomicBoolean.set(true);
        }, "t2").start();
    }

    //1.通过一个volatile变量实现
    private static void m1_volatile() {
        //kill stop......
        new Thread(() -> {
            while (true) {
                if (isStop) {
                    System.out.println(Thread.currentThread().getName() + "\t isStop被修改为true,程序停止");
                    break;
                }
                System.out.println("t1 .....hello volatile");
            }
        }, "t1").start();

        //暂停毫秒
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            isStop = true;
        }, "t2").start();
    }
}
