package com.futurebytedance.cf;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/4 - 0:54
 * @Description FutureTask
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<String>(new MyThread());

        //因为FutureTask实现了Runnable接口
        Thread t1 = new Thread(futureTask, "t1");
        t1.start();

        //获取Callable接口的返回值
        System.out.println(futureTask.get());
    }
}

//class MyThread implements Runnable{
//
//    @Override
//    public void run() {
//
//    }
//}

class MyThread implements Callable<String>{

    @Override
    public String call() throws Exception {
        System.out.println("------come in call()");
        return "hello Callable";
    }
}