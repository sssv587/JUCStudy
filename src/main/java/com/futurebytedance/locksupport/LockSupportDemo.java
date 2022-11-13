package com.futurebytedance.locksupport;

import java.util.concurrent.TimeUnit;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/13 - 21:08
 * @Description wait和notify实现等待和唤醒
 * 异常1:
 *  wait方法和notify方法,两个都去掉同步代码块
 * 异常2:
 *  将notify放在wait方法前面,程序无法执行,无法唤醒
 *
 * 总结:wait和notify方法必须要在同步块或者方法里面,且成对出现使用
 * 先wait后notify才OK
 */
public class LockSupportDemo {
    public static void main(String[] args) {
        Object objectLock = new Object();

        new Thread(() -> {
//            try {TimeUnit.MILLISECONDS.sleep(1000);} catch (Exception e) {e.printStackTrace();}
            synchronized (objectLock) {
                System.out.println(Thread.currentThread().getName() + "\t ...come in");
                try {
                    objectLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t ...被唤醒");
            }
        }, "t1").start();

        try {TimeUnit.MILLISECONDS.sleep(1000);} catch (Exception e) {e.printStackTrace();}

        new Thread(() -> {
            synchronized (objectLock) {
                objectLock.notify();
                System.out.println(Thread.currentThread().getName() + "\t ....发出通知");
            }
        }, "t2").start();
    }
}
