package com.futurebytedance.primary.sync;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/28 - 23:06
 * @Description 线程间通信-synchronized实现
 */
public class ThreadDemo1 {
    public static void main(String[] args) {
        //第三步 创建多个线程，调用资源类的操作方法
        Share share = new Share();
        //创建线程
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.incr();//+1
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.decr(); //-1
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();
    }
}

//第一步 创建资源类，定义属性和操作方法
class Share {
    //初始值
    private int number = 0;

    //+1的方法
    public synchronized void incr() throws InterruptedException {
        //第二步 判断 干活 通知
        if (number != 0) { //判断number值是否是0，如果不是，等待
            this.wait();
        }
        //如果number值是0，就+1操作
        number++;
        System.out.println(Thread.currentThread().getName() + " :: " + number);
        //通知其他线程
        this.notifyAll();
    }

    //-1的方法
    public synchronized void decr() throws InterruptedException {
        //判断
        if (number != 1) {
            this.wait();
        }
        //干活
        number--;
        System.out.println(Thread.currentThread().getName() + " :: " + number);
        //通知其他线程
        this.notifyAll();
    }
}