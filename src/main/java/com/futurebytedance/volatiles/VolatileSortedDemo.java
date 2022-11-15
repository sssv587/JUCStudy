package com.futurebytedance.volatiles;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/15 - 23:26
 * @Description Volatile有序性
 * 详见Volatile有序性.png
 */
public class VolatileSortedDemo {
    int i = 0;
    volatile boolean flag = false;
    public void write(){
        i = 2;
        flag = true;
    }
    public void read(){
        if(flag){
            System.out.println("---i = " + i);
        }
    }
}
