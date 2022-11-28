package com.futurebytedance.primary.sync;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/28 - 23:06
 * @Description 线程间通信-synchronized实现
 * 假设： 1、AA抢到锁执行 ++                 1
 * 　　   2、AA执行notify发现没有人wait，继续拿着锁执行 ，A判断不通过，A阻塞   1
 *  　　　3、BB抢到锁 ，B判断不通过，B阻塞   　1　 　　
 *        4、CC 抢到锁 执行--     　          　0
 * 　 　　5、CC 执行Notify 唤醒A， A执行++      1 　　　
 * 　 　　6、AA 执行notify唤醒B ，B执行++       2  （注意这个地方恰巧唤醒B，那么B 从哪阻塞的就从哪唤醒，B继续执行wait下面的++操作，导致出现2）
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

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.incr();//+1
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CC").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.decr(); //-1
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "DD").start();
    }
}

//第一步 创建资源类，定义属性和操作方法
class Share {
    //初始值
    private int number = 0;

    //+1的方法
    public synchronized void incr() throws InterruptedException {
        //第二步 判断 干活 通知
        //必须加上while不然会出现标题处的虚假唤醒问题
        while (number != 0) { //判断number值是否是0，如果不是，等待
            this.wait(); //在哪里睡，就会在哪里醒
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
        while (number != 1) {
            this.wait();
        }
        //干活
        number--;
        System.out.println(Thread.currentThread().getName() + " :: " + number);
        //通知其他线程
        this.notifyAll();
    }
}