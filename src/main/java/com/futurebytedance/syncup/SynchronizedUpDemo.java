package com.futurebytedance.syncup;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/23 - 0:40
 * @Description 锁升级之无锁、偏向锁、轻量锁、重量锁与锁升级后hashcode去哪了
 */
public class SynchronizedUpDemo {
    static final Object o = new Object();

    public static void main(String[] args) {
//        noLock();
//        biasedLock();
//        lightWeightLock();
//        heavyWeightLock();

        //先睡眠5秒,保证开启偏向锁
        try {TimeUnit.MILLISECONDS.sleep(5000);} catch (Exception e) {e.printStackTrace();}

//        Object o = new Object();
//        System.out.println("本应是偏向锁:");
//        System.out.println(ClassLayout.parseInstance(o).toPrintable());
//
//        o.hashCode();//没有重写,一致性哈希,重写后无效,当一个对象已经计算过identity hash code 它就无法进入偏向锁状态
//
//        synchronized (o){
//            System.out.println("本应是偏向锁,但是由于计算过一致性哈希,会直接升级为轻量级锁");
//            System.out.println(ClassLayout.parseInstance(o).toPrintable());
//        }

        synchronized (o){
            o.hashCode();
            System.out.println("偏向锁过程中遇到一致性哈希计算请求,立马撤销偏向模式,膨胀为重量级锁");
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }

    //重量级锁
    private static void heavyWeightLock() {
        new Thread(() -> {
            synchronized (o) {
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (o) {
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        }, "t2").start();

        new Thread(() -> {
            synchronized (o) {
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        }, "t3").start();
    }

    //轻量级锁
    private static void lightWeightLock() {
        Object o = new Object();
        new Thread(() -> {
            synchronized (o) {
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        }, "t1").start();
    }

    //偏向锁
    private static void biasedLock() {
        //演示偏向锁2:睡眠5s,默认4s后才会启动偏向锁
        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //演示偏向锁1:-XX:BiasedLockingStartupDelay=0即可
        //biased
        Object o = new Object();

//        synchronized (o){
//            System.out.println(ClassLayout.parseInstance(o).toPrintable());
//        }

        //第一种情况:可以看到,锁状态为101可偏向锁状态了,只是由于o对象未用synchronized加锁,所以线程id是空的
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        System.out.println("=============================================");

        //第二种情况:偏向锁带线程id的情况,第一行中后面不再是0了,有了线程id的值
        new Thread(() -> {
            synchronized (o) {
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        }, "t1").start();
    }

    //无锁
    private static void noLock() {
        Object o = new Object();

        System.out.println("10进制:" + o.hashCode());
        System.out.println("16进制:" + Integer.toHexString(o.hashCode()));
        System.out.println("2进制:" + Integer.toBinaryString(o.hashCode()));
        //00000001 10110110 00100111 01110011 01100111 00000000 00000000 00000000
        //1bit:0 unused 4bit:0000 分代年龄 1bit:0 偏向锁位 2bit:01 锁标志位 31bit:hashcode(如果有调用) unused:24bit
        //16进制:677327b6
        //         10110110 00100111 01110011 01100111 00000000 00000000 00000000
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
