package com.futurebytedance.objecthead;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/22 - 23:14
 * @Description JOL(Java Object Layout)
 */
public class JOLDemo {
    public static void main(String[] args) {
//        Thread.currentThread()
//        System.out.println(VM.current().details());

//        System.out.println(VM.current().objectAlignment());

//        Object o = new Object();//16bytes
//        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        Customers customers = new Customers();
        System.out.println(ClassLayout.parseInstance(customers).toPrintable());
    }
}

class Customers{
    //1.第一种情况,只有对象头,没有其他任何实例数据

    //2.第二种情况,int+boolean,默认满足对齐填充,24bytes
    int id;
    boolean flag = false;
    boolean flag2 = false;
}