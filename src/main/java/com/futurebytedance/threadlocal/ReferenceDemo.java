package com.futurebytedance.threadlocal;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/21 - 23:45
 * @Description ThreadLocal弱引用 引出问题
 * 强引用:当内存不足，JVM开始垃圾回收,对于强引用的对象,就算是出现了OOM也不会对该对象进行回收,死都不收
 * 软引用:对于只有软引用的对象来说,当系统内存充足时它 不会被回收,当系统内存不足时它会被回收
 * 弱引用:对于只有弱引用的对象来说,只要垃圾回收机制一运行,不管JVM的内存空间是否足够,都会回收该对象占用的内存
 * 虚引用:PhantomReference的get方法总是返回null
 *       它不能单独使用也不能通过它访问对象,虚引用必须和引用队列(ReferenceQueue)联合使用
 *       其意义在于:说明一个对象已经进入finalization阶段,可以被gc回收,用来实现比finalization机制更灵活的回收操作
 *
 * 软引用和弱引用的适用场景
 *      假如有一个应用需要读取大量的本地图片:
 *      如果每次读取图片都从硬盘读取则会严重影响性能,
 *      如果一次性全部加载到内存中又可能造成内存溢出。
 * 此时使用软引用可以解决这个问题。
 * 设计思路是：用一个HashMap来保存图片的路径和相应图片对象关联的软引用之间的映射关系，在内存不足时，JVM会自动回收这些缓存图片对象所占用的空间，从而有效地避免了OOM的问题。
 * Map<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();
 */
public class ReferenceDemo {
    public static void main(String[] args) {
//        strongReference();
//        softReference();
//        weakReference();
        phantomReference();
    }

    private static void phantomReference() {
        MyObject myObject = new MyObject();
        ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();
        PhantomReference<MyObject> phantomReference = new PhantomReference<>(myObject, referenceQueue);

//        System.out.println(phantomReference.get());//null

        List<byte[]> list = new ArrayList<>();

        new Thread(() -> {
            while (true){
                list.add(new byte[1024 * 1024 * 1024]);
                try {TimeUnit.MILLISECONDS.sleep(500);} catch (Exception e) {e.printStackTrace();}
                System.out.println(phantomReference.get() + "\t" + "list add ok");
            }
        },"t1").start();

        new Thread(() -> {
            while (true) {
                Reference<? extends MyObject> reference = referenceQueue.poll();
                if (reference != null) {
                    System.out.println("-------有虚对象回收加入了队列");
                    break;
                }
            }
        }, "t2").start();
    }

    private static void weakReference() {
        WeakReference<MyObject> weakReference = new WeakReference<>(new MyObject());
        System.out.println("-------gc before 内存够用:" + weakReference.get());

        System.gc();

        try {TimeUnit.SECONDS.sleep(1);} catch (Exception e) {e.printStackTrace();}

        System.out.println("-------gc before 内存够用:" + weakReference.get());
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
