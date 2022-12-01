package com.futurebytedance.primary.juc;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/12/1 - 23:15
 * @Description Semaphore
 * Semaphore的构造方法中传入的第一个参数是最大信号量(可以看成最大线程池),每个信号量初始化为一个最多只能分发一个许可证。
 * 使用acquire方法获得许可证,release方法释放许可
 */
public class SemaphoreDemo {
    //6量汽车，停3个车位
    public static void main(String[] args) {
        //创建Semaphore,设置许可数量
        Semaphore semaphore = new Semaphore(3);

        //模拟6辆汽车
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    //抢占
                    semaphore.acquire();

                    System.out.println(Thread.currentThread().getName() + " 抢到了车位");

                    //设置随机停车时间
                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));

                    System.out.println(Thread.currentThread().getName() + " 离开了车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //释放
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}
