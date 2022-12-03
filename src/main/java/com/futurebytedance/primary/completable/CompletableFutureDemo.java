package com.futurebytedance.primary.completable;

import java.util.concurrent.CompletableFuture;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/12/3 - 18:25
 * @Description 异步回调
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws Exception {
        //异步调用 没有返回值
        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " completableFuture1");
        });
        completableFuture1.get();

        //异步调用 有返回值
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " completableFuture2");
            return 1024;
        });
        completableFuture2.whenComplete((a, b) -> {
            System.out.println("-----a \t" + a);
            System.out.println("-----b \t" + b);
        }).get();
    }
}
