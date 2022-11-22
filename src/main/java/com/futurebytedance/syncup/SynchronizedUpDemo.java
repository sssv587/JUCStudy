package com.futurebytedance.syncup;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/23 - 0:40
 * @Description 锁升级之无锁
 */
public class SynchronizedUpDemo {
    public static void main(String[] args) {
        Object o = new Object();

        System.out.println("10进制:" + o.hashCode());
        System.out.println("16进制:" + Integer.toHexString(o.hashCode()));
        System.out.println("2进制:" + Integer.toBinaryString(o.hashCode()));
        //00000001 10110110 00100111 01110011 01100111 00000000 00000000 00000000
        //1bit:0 unused 4bit:0000 分代年龄 1bit:0 偏向锁位 2bit:01 锁标志位 31bit:hashcode(如果有调用) unused:24bit
        //16进制:677327b6
        //         10110110 00100111 01110011 01100111 00000000 00000000 00000000
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
