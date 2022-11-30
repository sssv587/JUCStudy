package com.futurebytedance.primary.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/12/1 - 1:08
 * @Description 比较Runnable接口和Callable接口、Callable接口创建
 */
public class Demo1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //Runnable接口创建线程
        new Thread(new MyThread1(), "AA").start();

        //Callable接口创建线程

        //FutureTask
        FutureTask<Integer> futureTask1 = new FutureTask<>(new MyThread2());

        //lambda表达式
        FutureTask<Integer> futureTask2 = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() + " come in callable");
            return 1024;
        });

        //创建一个线程
        new Thread(futureTask2, "lucy").start();
        new Thread(futureTask1, "mary").start();

        while (!futureTask2.isDone()) {
            System.out.println("wait....");
        }

        //调用FutureTask的get方法
        System.out.println(futureTask1.get());

        System.out.println(futureTask2.get());

        System.out.println(Thread.currentThread().getName()+" come over");

        //FutureTask原理 未来任务
        /*
         * 1、老师上课，口渴了，去买票不合适，讲课线程继续。
         *   单开启线程找班上班长帮我买水，把水买回来，需要时候直接get
         *
         * 2、4个同学， 1同学 1+2...5   ，  2同学 10+11+12....50， 3同学 60+61+62，  4同学 100+200
         *      第2个同学计算量比较大，
         *     FutureTask单开启线程给2同学计算，先汇总 1 3 4 ，最后等2同学计算位完成，统一汇总
         *
         * 3、考试，做会做的题目，最后看不会做的题目
         *
         * 汇总一次
         *
         */
    }
}

//比较两个接口
//实现Runnable接口
class MyThread1 implements Runnable {

    @Override
    public void run() {

    }
}

//实现Callable接口
class MyThread2 implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " come in callable");
        return 200;
    }
}