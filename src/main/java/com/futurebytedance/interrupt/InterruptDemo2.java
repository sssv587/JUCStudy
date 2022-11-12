package com.futurebytedance.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/12 - 20:57
 * @Description 中断协商案例之interrupt()
 * 仅仅是设置线程的中断状态位设置为true,不会停止线程
 *
 * 1.如果线程处于正常活动状态，那么会将该线程的中断标志设置为 true，仅此而已。
 * 被设置中断标志的线程将继续正常运行，不受影响。所以， interrupt() 并不能真正的中断线程，需要被调用的线程自己进行配合才行。
 *
 * 2.如果线程处于被阻塞状态（例如处于sleep, wait, join 等状态），在别的线程中调用当前线程对象的interrupt方法，
 * 那么线程将立即退出被阻塞状态，并抛出一个InterruptedException异常。
 *
 * 总结:中断只是一种协商机制,修改中断标志位仅此而已,不是立刻stop打断
 */
public class InterruptDemo2 {
    public static void main(String[] args) {
        //实例方法interrupt()仅仅是设置线程的中断状态位设置为true,不会停止线程
        Thread t1 = new Thread(() -> {
            for (int i = 0; i <= 300; i++) {
                System.out.println("------" + i);
            }
            System.out.println("t1线程调用interrupt()后的中断标识02:" + Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();

        System.out.println("t1线程默认的中断标识:" + t1.isInterrupted()); //false

        //活动状态,t1线程还在执行中
        //暂停毫秒
        try {
            TimeUnit.MILLISECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        t1.interrupt();//true
        System.out.println("t1线程调用interrupt()后的中断标识01:" + t1.isInterrupted());//true

        //非活动状态,t1线程不在执行中，已经结束执行了。
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("t1线程调用interrupt()后的中断标识03:" + t1.isInterrupted());//false
    }
}
