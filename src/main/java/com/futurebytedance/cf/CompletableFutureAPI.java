package com.futurebytedance.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/7 - 0:01
 * @Description CompletableFuture常用方法
 * 1.获得结果和触发计算
 *     获取结果
 *         1.public T get(); 不见不散,需要抛异常
 *         2.public T get(long timeout,TimeUnit unit); 过时不候,需要抛异常
 *         3.public T join(); 不见不散,不需要抛异常
 *         4.public T getNow(T valueIfAbsent); 计算完,返回计算完成后的结果;没算完,返回设定的valueIfAbsent值
 *     主动触发计算
 *         1.public boolean complete(T value);如果执行完了,返回正常结果和false;返回complete的value值和false
 */
public class CompletableFutureAPI {
    public static void main(String[] args) {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "abc";
        });

        //不见不散,需要抛异常
        //System.out.println(completableFuture.get());
        //过时不候,需要抛异常
        //System.out.println(completableFuture.get(2L, TimeUnit.SECONDS));
        //不见不散,不需要抛异常
        //System.out.println(completableFuture.join());
        try {TimeUnit.SECONDS.sleep(2);} catch (Exception e) {e.printStackTrace();}
        //计算完,返回计算完成后的结果;没算完,返回设定的valueIfAbsent值
        //System.out.println(completableFuture.getNow("xxx"));

        //如果执行完了,返回正常结果和false;返回complete的value值和false
        System.out.println(completableFuture.complete("completeValue") + "\t" + completableFuture.join());
    }
}
