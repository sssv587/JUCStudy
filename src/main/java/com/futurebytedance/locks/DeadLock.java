package com.futurebytedance.locks;

import java.util.concurrent.TimeUnit;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/9 - 0:25
 * @Description 死锁
 * 死锁是指两个或两个以上的线程在执行过程中,因争夺资源而造成的一种互相等待的现象,若无外力干涉那它们都将无法推进下去，如果系统资源充足，进程的资源请求都能够得到满足，死锁出现的可能性就很低，否则就会因争夺有限的资源而陷入死锁。
 *     产生死锁的原因:
 *         1. 系统资源不足
 *         2. 进程运行推进的顺序不合适
 *         3. 资源分配不当
 *  jps查看进程与进程ID
 *  jstack+进程ID 查看原因
 */
public class DeadLock {
    public static void main(String[] args) {
        final Object objectA = new Object();
        final Object objectB = new Object();

        new Thread(() -> {
            synchronized (objectA){
                System.out.println(Thread.currentThread().getName() + "\t 自己持有A锁,希望获取B锁");
                try {TimeUnit.MILLISECONDS.sleep(500);} catch (Exception e) {e.printStackTrace();}
                synchronized (objectB){
                    System.out.println(Thread.currentThread().getName() + "\t 成功获得B锁");
                }
            }
        },"A").start();

        new Thread(() -> {
            synchronized (objectB){
                System.out.println(Thread.currentThread().getName() + "\t 自己持有B锁,希望获取A锁");
                try {TimeUnit.MILLISECONDS.sleep(500);} catch (Exception e) {e.printStackTrace();}
                synchronized (objectA){
                    System.out.println(Thread.currentThread().getName() + "\t 成功获得A锁");
                }
            }
        },"B").start();
    }
}
