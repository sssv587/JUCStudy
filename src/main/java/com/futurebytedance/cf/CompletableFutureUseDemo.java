package com.futurebytedance.cf;

import java.util.concurrent.*;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/6 - 22:26
 * @Description CompletableFutureUse的使用
 */
public class CompletableFutureUseDemo {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        try {
            CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName() + "-----come in");
                int result = ThreadLocalRandom.current().nextInt(10);
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("-----1秒钟后出结果:" + result);
                if (result > 5) {
                    int i = 10 / 0;
                }
                return result;
            }, threadPool).whenComplete((v, e) -> {
                if (e == null) {
                    System.out.println("-----------计算完成,更新系统UpdateValue:" + v);
                }
            }).exceptionally(e -> {
                e.printStackTrace();
                System.out.println("异常情况:" + e.getCause() + "\t" + e.getMessage());
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

        System.out.println(Thread.currentThread().getName() + "线程先去忙其他任务");

        //ForkJoinPool感觉很像一个守护线程,如果主线程执行完了,会直接关闭任务
        //主线程不要立刻结束,否则CompletableFuture默认使用的线程池会立刻关闭:暂停3秒钟线程
//        try {
//            TimeUnit.MILLISECONDS.sleep(3000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    //完全可以支持future的功能
    private static void future1() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "-----come in");
            int result = ThreadLocalRandom.current().nextInt(10);
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("-----1秒钟后出结果:" + result);
            return result;
        });

        System.out.println(Thread.currentThread().getName() + "线程先去忙其他任务");

        System.out.println(completableFuture.get());
    }
}
