package com.futurebytedance.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/20 - 16:12
 * @Description CAS之手写自旋锁
 * 题目：实现一个自旋锁
 * 自旋锁好处：循环比较获取没有类似wait的阻塞。
 * <p>
 * 通过CAS操作完成自旋锁，A线程先进来调用myLock方法自己持有锁5秒钟，B随后进来后发现
 * 当前有线程持有锁，不是null，所以只能通过自旋等待，直到A释放锁后B随后抢到。
 */
public class SpinLockDemo {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t" + "-------come in");
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    public void unLock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "\t" + "-------task over,unlock...");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(() -> {
            spinLockDemo.lock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
            spinLockDemo.unLock();
        }, "A").start();

        //暂停500毫秒,线程A先于B启动
        try {TimeUnit.MILLISECONDS.sleep(500);} catch (Exception e) {e.printStackTrace();}

        new Thread(() -> {
            spinLockDemo.lock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
            spinLockDemo.unLock();
        }, "B").start();
    }
}
