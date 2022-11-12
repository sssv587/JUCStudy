package com.futurebytedance.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/12 - 21:12
 * @Description 中断协商案例之其他线程调用interrupt()
 * 2.如果线程处于被阻塞状态（例如处于sleep, wait, join 等状态），在别的线程中调用当前线程对象的interrupt方法，
 * 那么线程将立即退出被阻塞状态，并抛出一个InterruptedException异常。
 *
 * 总结:中断只是一种协商机制,修改中断标志位仅此而已,不是立刻stop打断
 *
 * 1. 中断标识位,默认false
 * 2. t2 ---> t1发出了中断协商,t2调用t1.interrupt(),中断标识位true
 * 3. 中断标志位true,正常情况,程序停止
 * 4. 中断标志位true,异常情况,InterruptedException,将会把中断状态清除,并且收到InterruptedException。中断标志位false
 *      导致无线循环
 * 5. 在catch块中,需要再次给中断标志位设置为true,2次调用停止程序才OK
 */
public class InterruptDemo3 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true){
                if(Thread.currentThread().isInterrupted()){
                    System.out.println(Thread.currentThread().getName() + "\t 中断标识位"
                    + Thread.currentThread().isInterrupted() + "程序停止");
                    break;
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();//为什么要在异常处,再调用一次?
                    e.printStackTrace();
                }

                System.out.println("-----hello InterruptDemo3");
            }
        }, "t1");

        t1.start();

        try {TimeUnit.MILLISECONDS.sleep(1000);} catch (Exception e) {e.printStackTrace();}

        new Thread(() -> t1.interrupt(),"t2").start();
    }
}
