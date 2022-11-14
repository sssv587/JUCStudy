package com.futurebytedance.volatiles;

import java.util.concurrent.TimeUnit;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/14 - 23:53
 * @Description volatile可见性
 * 不加volatile,没有可见性,程序无法停止
 * 加了volatile,保证可见性,程序可以停止
 *
 * 问题可能:
 * 1. 主线程修改了flag之后没有将其刷新到主内存,所以t1线程看不到。
 * 2. 主线程将flag刷新到了主内存,但是t1一直读取的是自己工作内存中flag的值,没有去主内存中更新获取flag最新的值。
 *
 * 我们的诉求：
 * 1.线程中修改了工作内存中的副本之后,立即将其刷新到主内存;
 * 2.工作内存中每次读取共享变量时,都去主内存中重新读取,然后拷贝到工作内存。
 *
 * 解决：
 * 使用volatile修饰共享变量,就可以达到上面的效果,被volatile修改的变量有以下特点：
 * 1.线程中读取的时候,每次读取都会去主内存中读取共享变量最新的值,然后将其复制到工作内存
 * 2.线程中修改了工作内存中变量的副本,修改之后会立即刷新到主内存
 */
public class VolatileSeeDemo {
//    static boolean flag = true;
    static volatile boolean flag = true;

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ------come in");
            while (flag) {

            }
            System.out.println(Thread.currentThread().getName() + "\t ------flag被设置为false,程序停止");
        }, "t1").start();

        try {TimeUnit.SECONDS.sleep(2);} catch (Exception e) {e.printStackTrace();}

        flag = false;

        System.out.println(Thread.currentThread().getName() + "\t 修改完成flag:" + flag);
    }
}
