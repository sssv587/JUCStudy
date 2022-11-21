package com.futurebytedance.threadlocal;

import java.util.concurrent.TimeUnit;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/21 - 23:45
 * @Description ThreadLocal弱引用 引出问题
 * 强、软、弱、虚引用
 */
public class ReferenceDemo {
    public static void main(String[] args) {
//        strongReference();
    }

    //强引用
    private static void strongReference() {
        MyObject myObject = new MyObject();
        System.out.println("gc before:" + myObject);

        myObject = null;
        System.gc();//人工开启GC,一般不用

        try {TimeUnit.MILLISECONDS.sleep(500);} catch (Exception e) {e.printStackTrace();}

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
