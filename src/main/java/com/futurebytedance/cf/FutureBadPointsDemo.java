package com.futurebytedance.cf;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/5 - 17:06
 * @Description FutureTask的缺点
 * 1.get容易导致阻塞,一般建议放在程序后面,一旦调用不见不散,非要等到结果才会离开,不管你是否计算完成,容易程序堵塞
 * 2.假如我不愿意等到很长时间,我希望过时不候,可以自动离开
 * 3.isDone轮询的方式会耗费无谓的CPU资源,而且也不见得能及时的得到计算结果
 * 如果想要异步获取结果,通常都会以轮询的方式去获取结果,尽量不要阻塞
 *
 *
 * Future对于结果的获取不是很友好,只能通过阻塞或轮询的方式得到任务的结果
 */
public class FutureBadPointsDemo {
    public static void main(String[] args) throws Exception {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ----come in");
            //暂停几秒钟线程
            try {
                TimeUnit.MILLISECONDS.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "task over";
        });

        Thread t1 = new Thread(futureTask, "t1");
        t1.start();

        //放在上面,会阻塞下面的main线程
//        System.out.println(futureTask.get()); //不见不散,非要等到结果才会离开,不管你是否计算完成,容易程序堵塞

        System.out.println(Thread.currentThread().getName() + "\t ------忙其他任务了");

//        System.out.println(futureTask.get());

        //Exception in thread "main" java.util.concurrent.TimeoutException
//        System.out.println(futureTask.get(3, TimeUnit.SECONDS));

        while (true) {
            if (futureTask.isDone()) {
                System.out.println(futureTask.get());
                break;
            } else {
                //暂停毫秒
                try {TimeUnit.MILLISECONDS.sleep(500);} catch (Exception e) {e.printStackTrace();}
                System.out.println("正在处理中,不要再催了,越催越慢,再催熄火");
            }
        }
    }
}
