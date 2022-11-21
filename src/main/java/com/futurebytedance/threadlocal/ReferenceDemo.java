package com.futurebytedance.threadlocal;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/21 - 23:45
 * @Description ThreadLocal弱引用 引出问题
 * 强引用:当内存不足，JVM开始垃圾回收,对于强引用的对象,就算是出现了OOM也不会对该对象进行回收,死都不收
 * 软引用:对于只有软引用的对象来说,当系统内存充足时它 不会被回收,当系统内存不足时它会被回收
 */
public class ReferenceDemo {
    public static void main(String[] args) {
//        strongReference();
        softReference();
    }

    private static void softReference() {
        SoftReference<MyObject> softReference = new SoftReference<>(new MyObject());
//        System.out.println("-------softReference:" + softReference.get());

        System.gc();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("-------gc after内存够用:" + softReference.get());

        //设置-Xms10m -Xmx10m
        try {
            byte[] bytes = new byte[20 * 1024 * 1024]; //20M对象
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("-------gc after内存不够:" + softReference.get());
        }
    }

    //强引用:当内存不足，JVM开始垃圾回收,对于强引用的对象,就算是出现了OOM也不会对该对象进行回收,死都不收
    private static void strongReference() {
        MyObject myObject = new MyObject();
        System.out.println("gc before:" + myObject);

        myObject = null;
        System.gc();//人工开启GC,一般不用

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("gc after:" + myObject);
    }
}

class MyObject {
    //这个方法一般不用复写,只是为了演示案例
    @Override
    protected void finalize() throws Throwable {
        //finalize的通常目的是在对象被不可撤销地丢弃之前执行清理操作。
        System.out.println("-------invoke finalize method!");
    }
}
