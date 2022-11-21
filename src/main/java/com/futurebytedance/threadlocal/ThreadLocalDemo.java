package com.futurebytedance.threadlocal;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/21 - 19:06
 * @Description ThreadLocal案例
 * 需求1：5个销售卖房子,集团高层只关心销售总量的准确统计数
 * <p>
 * 需求2:5个销售卖完随机数房子,各自独立销售额度,自己业绩按提成走,分灶吃饭,各个销售自己动手,丰衣足食
 */
public class ThreadLocalDemo {
    public static void main(String[] args) {
        House house = new House();

        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                int size = new Random().nextInt(5) + 1;
//                System.out.println(size);
                try {
                    for (int j = 1; j <= size; j++) {
                        house.saleHouse();
                        house.saleVolumeByThreadLocal();
                    }
                    System.out.println(Thread.currentThread().getName() + "号销售卖出:" + house.saleVolume.get());
                } finally {
                    //防止内存泄漏或线程池复用
                    house.saleVolume.remove();
                }
            }, String.valueOf(i)).start();
        }

        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "\t 共计卖出多少套:" + house.saleCount);
    }
}

class House {
    int saleCount = 0;

    public synchronized void saleHouse() {
        ++saleCount;
    }

    //匿名内部类写法
//    ThreadLocal<Integer> saleVolume = new ThreadLocal<Integer>() {
//        @Override
//        protected Integer initialValue() {
//            return 0;
//        }
//    };

    ThreadLocal<Integer> saleVolume = ThreadLocal.withInitial(() -> 0);

    public void saleVolumeByThreadLocal() {
        saleVolume.set(1 + saleVolume.get());
    }
}