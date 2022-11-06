package com.futurebytedance.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/7 - 0:16
 * @Description CompletableFuture对计算结果进行处理
 * 2.对计算结果进行处理
 *     1.thenApply:计算结果存在依赖关系,这个线程串行化;异常相关(直接中止)
 *     2.handle:计算结果存在依赖关系,这个线程串行化;异常相关(有异常也可以向下走一步)
 */
public class CompletableFutureAPI2 {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        //thenApply方法,出现异常会直接中止后续程序运行
//        CompletableFuture.supplyAsync(() -> {
//            try {
//                TimeUnit.MILLISECONDS.sleep(500);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            System.out.println("111");
//            return 1;
//        },threadPool).thenApply(f -> {
////            int i = 10/0;
//            System.out.println("222");
//            return f + 2;
//        }).thenApply(f -> {
//            System.out.println("333");
//            return f + 3;
//        }).whenComplete((v, e) -> {
//            if (e == null) {
//                System.out.println("-----计算结果:" + v);
//            }
//        }).exceptionally(e -> {
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//            return null;
//        });

        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("111");
            return 1;
        }, threadPool).handle((f, e) -> {
            int i = 10/0;
            System.out.println("222");
            return f + 2;
        }).handle((f, e) -> {
            System.out.println("333");
            return f + 3;
        }).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println("-----计算结果:" + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        });

        System.out.println(Thread.currentThread().getName() + "----主线程先去忙其他任务");

        //主线程不要立刻结束,否则CompletableFuture默认使用的线程池会立刻关闭
        //try {TimeUnit.SECONDS.sleep(2);} catch (Exception e) {e.printStackTrace();}

        threadPool.shutdown();
    }
}
