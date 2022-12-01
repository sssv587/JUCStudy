package com.futurebytedance.primary.juc;

import java.util.concurrent.CountDownLatch;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/12/1 - 22:50
 * @Description CountDownLatch
 * CountDownLatch类可以设置一个计数器，然后通过 countDown方法来进行减1的操作，使用await方法等待计数器不大于0，然后继续执行await方法之后的语句。
 *   CountDownLatch 主要有两个方法，当一个或多个线程调用 await 方法时，这些线程会阻塞
 *   其它线程调用 countDown 方法会将计数器减 1(调用 countDown 方法的线程不会阻塞)
 *   当计数器的值变为 0 时，因 await 方法阻塞的线程会被唤醒，继续执行
 */
public class CountDownLatchDemo {
    //6个同学陆续离开教室之后，班长锁门
    public static void main(String[] args) throws InterruptedException {
        //创建CountDownLatch对象
        CountDownLatch countDownLatch = new CountDownLatch(6);

        //6个同学陆续离开教室之后
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 号同学离开了教师");

                //计数 -1
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        //等待
        countDownLatch.await();

        System.out.println(Thread.currentThread().getName() + " 班长锁门走人了");
    }
}
