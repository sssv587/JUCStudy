package com.futurebytedance.objecthead;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/22 - 11:52
 * @Description 对象内存布局
 */
public class ObjectHeadDemo {
    public static void main(String[] args) {
        Object o = new Object(); //new一个对象,占内存多少?
        //在64位系统中,Mark Word占了8个字节,类型指针占了8个字节,一共是16个字节

        System.out.println(o.hashCode());//这个hashcode记录在对象的什么地方

        synchronized (o) {

        }

        System.gc();//手动手机垃圾。。。。。15次可以从新生代---养老区
    }
}
