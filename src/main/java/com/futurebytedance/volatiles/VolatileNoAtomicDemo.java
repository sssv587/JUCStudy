package com.futurebytedance.volatiles;

import java.util.concurrent.TimeUnit;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/15 - 0:17
 * @Description volatile没有原子性
 * volatile的复合操作不具有原子性,比如number++
 *
 * volatile变量不适合参与到依赖当前值的运算
 */
public class VolatileNoAtomicDemo {
    public static void main(String[] args) {
        MyNumber myNumber = new MyNumber();

        for (int i = 1; i <=10; i++) {
            new Thread(() -> {
                for (int j = 1; j <=1000; j++) {
                    myNumber.addPlusPlus();
                }
            },String.valueOf(i)).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(myNumber.number);
    }
}

class MyNumber {
    int number;

    public void addPlusPlus() {
        number++;
    }
}