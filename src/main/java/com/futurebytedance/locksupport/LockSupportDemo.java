package com.futurebytedance.locksupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/13 - 21:08
 * @Description LockSupport
 * 1.wait和notify实现等待和唤醒
 * 异常1:
 * wait方法和notify方法,两个都去掉同步代码块
 * 异常2:
 * 将notify放在wait方法前面,程序无法执行,无法唤醒
 * <p>
 * 总结:wait和notify方法必须要在同步块或者方法里面,且成对出现使用
 * 先wait后notify才OK
 * <p>
 * 2.await和signal实现等待和唤醒
 * 异常1:
 * 去掉lock/unlock
 * 异常2:
 * 先signal后await
 * 总结:Condition中的线程等待和唤醒方法,需要先获取锁
 * 一定要先await后signal,不要反了
 * <p>
 * 两个对象Object和Condition使用的限制条件
 *   线程先要获得并持有锁,必须在锁块(synchronized或lock)中,必须要先等待后唤醒,线程才能够被唤醒
 *
 * 3.LockSupport的park与unpark方法
 * 正常+无锁块要求
 * 之前错误的先唤醒后等待,LockSupport照样支持
 * 成双成对要牢记
 */
public class LockSupportDemo {
    public static void main(String[] args) {
//        syncWaitNotify();

//        lockAwaitSignal();

        Thread t1 = new Thread(() -> {
            try {TimeUnit.MILLISECONDS.sleep(3000);} catch (Exception e) {e.printStackTrace();}
            System.out.println(Thread.currentThread().getName() + "\t ....come in" + System.currentTimeMillis());
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "\t ....被唤醒" + System.currentTimeMillis());
        }, "t1");

        t1.start();

//        try {
//            TimeUnit.MILLISECONDS.sleep(1000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        new Thread(() -> {
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName() + "\t ...发出通知");
        }, "t2").start();
    }

    private static void lockAwaitSignal() {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(() -> {
//            try {TimeUnit.MILLISECONDS.sleep(1000);} catch (Exception e) {e.printStackTrace();}
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t ....come in");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "\t ....被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1").start();

//        try {
//            TimeUnit.MILLISECONDS.sleep(1000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        new Thread(() -> {
            lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "\t ...发出通知");
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }

    private static void syncWaitNotify() {
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

        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            synchronized (objectLock) {
                objectLock.notify();
                System.out.println(Thread.currentThread().getName() + "\t ....发出通知");
            }
        }, "t2").start();
    }
}
