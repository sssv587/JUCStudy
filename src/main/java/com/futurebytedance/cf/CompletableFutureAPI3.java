package com.futurebytedance.cf;

import java.util.concurrent.CompletableFuture;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/7 - 0:26
 * @Description CompletableFuture的使用
 * 3.对计算结果进行消费
 *     1.thenAccept:接收任务的处理结果,并消费处理,无返回结果
 *     Code任务之间的顺序执行:
 *         thenRun(Runnable runnable):任务A执行完执行B,并且B不需要A的结果
 *         thenAccept(Consumer action):任务A执行完执行B,B需要A的结果,但是任务B无返回值
 *         thenApply(Function fn):任务A执行完执行B,B需要A的结果,同时任务B有返回值
 */
public class CompletableFutureAPI3 {
    public static void main(String[] args) {
//        CompletableFuture.supplyAsync(() -> 1).thenApply(f -> f + 2).thenApply(f -> f + 3)
//                .thenAccept(System.out::println);

        //thenRun(Runnable runnable):任务A执行完执行B,并且B不需要A的结果
        System.out.println(CompletableFuture.supplyAsync(() -> {
            System.out.println("A执行了");
            return "resultA";
        }).thenRun(() -> {
            System.out.println("thenRun执行了...");
        }).join());

        //thenAccept(Consumer action):任务A执行完执行B,B需要A的结果,但是任务B无返回值
        System.out.println(CompletableFuture.supplyAsync(() -> {
            System.out.println("A执行了");
            return "resultA";
        }).thenAccept(System.out::println).join());

        //thenApply(Function fn):任务A执行完执行B,B需要A的结果,同时任务B有返回值
        System.out.println(CompletableFuture.supplyAsync(() -> {
            System.out.println("A执行了");
            return "resultA";
        }).thenApply(r -> r + "resultB").join());
    }
}
