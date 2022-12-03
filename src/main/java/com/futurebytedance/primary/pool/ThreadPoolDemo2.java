package com.futurebytedance.primary.pool;

import java.util.concurrent.*;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/12/3 - 18:00
 * @Description 自定义线程池
 */
public class ThreadPoolDemo2 {
    public static void main(String[] args) {

        ExecutorService threadPoolExecutor = new ThreadPoolExecutor(
                2,
                5, 2L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        try {
            for (int i = 1; i <= 10; i++) {
                //执行
                threadPoolExecutor.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭
            threadPoolExecutor.shutdown();
        }
    }
}
