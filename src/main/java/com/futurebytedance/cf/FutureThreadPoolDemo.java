package com.futurebytedance.cf;

import java.util.concurrent.*;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/5 - 16:43
 * @Description FutureThreadPool
 * Future+线程池异步多线程任务配合,能显著提高程序的执行效率
 */
public class FutureThreadPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //3个任务,目前开启3个任务线程来处理,请问耗时多少
        long startTime = System.currentTimeMillis();
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        FutureTask<String> futureTask1 = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "task1 over";
        });
        threadPool.submit(futureTask1);

        FutureTask<String> futureTask2 = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "task2 over";
        });

        threadPool.submit(futureTask2);

        FutureTask<String> futureTask3 = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "task3 over";
        });

        threadPool.submit(futureTask3);

        System.out.println(futureTask1.get());
        System.out.println(futureTask2.get());
        System.out.println(futureTask3.get());

        long endTime = System.currentTimeMillis();
        System.out.println("---costTime:" + (endTime - startTime) + " 毫秒");

        threadPool.shutdown();
    }

    public static void m1() {
        //3个任务,目前只有一个main线程来处理,请问耗时多少
        long startTime = System.currentTimeMillis();

        //暂停毫秒
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("---costTime:" + (endTime - startTime) + " 毫秒");

        System.out.println(Thread.currentThread().getName() + "\t ------end");
    }
}
