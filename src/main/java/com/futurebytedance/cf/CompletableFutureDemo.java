package com.futurebytedance.cf;

import java.util.concurrent.Callable;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/4 - 0:54
 * @Description FutureTask
 */
public class CompletableFutureDemo {
    public static void main(String[] args) {

    }
}

class MyThread implements Runnable{

    @Override
    public void run() {

    }
}

class MyThread2 implements Callable<String>{

    @Override
    public String call() throws Exception {
        return null;
    }
}