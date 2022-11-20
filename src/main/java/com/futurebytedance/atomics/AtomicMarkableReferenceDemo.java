package com.futurebytedance.atomics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/20 - 17:37
 * @Description 引用类型原子类
 * CAS---Unsafe----do while+ABA----AtomicStampedReference,AtomicMarkableReference
 * <p>
 * AtomicStampedReference,version号,+1,解决修改过几次
 * <p>
 * AtomicMarkableReference,一次,false,true,解决是否修改过(类似一次性筷子)
 */
public class AtomicMarkableReferenceDemo {
    static AtomicMarkableReference<Integer> markableReference = new AtomicMarkableReference<>(100, false);

    public static void main(String[] args) {
        new Thread(() -> {
            boolean marked = markableReference.isMarked();
            System.out.println(Thread.currentThread().getName() + "\t" + "默认标识:" + marked);
            //暂停1秒钟线程,等待后面的T2线程和我拿到一样的模式flag标识
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            markableReference.compareAndSet(100, 1000, marked, !marked);
        }, "t1").start();

        new Thread(() -> {
            boolean marked = markableReference.isMarked();
            System.out.println(Thread.currentThread().getName() + "\t" + "默认标识:" + marked);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            boolean b = markableReference.compareAndSet(100, 5000, marked, !marked);
            System.out.println(Thread.currentThread().getName() + "\t" + "t2线程CAS result:" + b);
            System.out.println(Thread.currentThread().getName() + "\t" + markableReference.isMarked());
            System.out.println(Thread.currentThread().getName() + "\t" + markableReference.getReference());
        }, "t2").start();
    }
}