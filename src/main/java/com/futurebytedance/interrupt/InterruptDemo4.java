package com.futurebytedance.interrupt;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/12 - 21:33
 * @Description 静态方法Thread.interrupted()
 * //中断状态将会根据传入的ClearInterrupted参数值确定是否重置,如果传递的是false,代表不清除中断标志位,反之,清理中断标志位
 * public static boolean interrupted() {
 *         return currentThread().isInterrupted(true);
 *     }
 *
 * public boolean isInterrupted() {
 *         return isInterrupted(false);
 *     }
 *
 * private native boolean isInterrupted(boolean ClearInterrupted);
 */
public class InterruptDemo4 {
    public static void main(String[] args) {
        //作测试当前线程是否被中断(检查中断标志),返回一个boolean并清除中断状态，
        //第二次再调用时中断状态已经被清除，将返回一个false。
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted()); //false
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted()); //false
        System.out.println("-----1");
        Thread.currentThread().interrupt();//中断标志位设置为true
        System.out.println("-----2");
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted()); //true
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted()); //false


        Thread.interrupted(); //静态方法
        Thread.currentThread().isInterrupted(); //实例方法
    }
}
