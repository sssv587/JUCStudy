package com.futurebytedance.threadlocal;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/21 - 23:45
 * @Description ThreadLocal弱引用
 */
public class ReferenceDemo {
    public static void main(String[] args) {

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
