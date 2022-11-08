package com.futurebytedance.locks;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/8 - 23:08
 * @Description 多线程之公平锁与非公平锁
 * 公平锁:是指多个线程按照申请锁的顺序来获取锁,类似排队买票 ReentrantLock lock = new ReentrantLock(true);
 * 非公平锁:是指多个线程获取锁的顺序并不是按照申请锁的顺序,有可能后申请的线程比先申请的线程优先获得锁
 *      在高并发环境下,有可能造成优先级反转或者饥饿的状态 ReentrantLock lock = new ReentrantLock(false);//默认
 *
 * 为什么会有公平锁与非公平锁的设计?为什么默认非公平?
 * 1.恢复挂起的线程到真正锁的获取还是有时间差的,从开发人员来看这个时间微乎其微,但是从CPU的角度来看,这个事件差存在的还是很明显的。
 *     所以非公平锁能更充分的利用CPU的时间片,尽量减少CPU空闲状态时间
 * 2.使用多线程很重要的考量点就是线程切换的开销,当采用非公平锁时,当一个线程请求锁获取同步状态,然后释放同步状态,所以刚释放锁的线程
 *     在此刻再次获取同步状态的概率就变得非常大,所以就减少了线程的开销
 *
 * 什么时候用公平锁?什么时候用非公平锁?
 *   如果为了更高的吞吐量,很显然非公平锁是比较合适的,因为节省很多线程切换时间,吞吐量自然就上去了
 *   否则那就用公平锁,大家公平使用
 */
public class SaleTicket {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(() -> {
            for (int i = 0; i <= 55; i++) {
                ticket.sale();
            }
        }, "a").start();
        new Thread(() -> {
            for (int i = 0; i <= 55; i++) {
                ticket.sale();
            }
        }, "b").start();
        new Thread(() -> {
            for (int i = 0; i <= 55; i++) {
                ticket.sale();
            }
        }, "c").start();
    }
}

//资源类,模拟3个售票员卖完50张票
class Ticket {
    private int number = 50;
    ReentrantLock lock = new ReentrantLock();

    public void sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出第:\t" + (number--) + "\t 还剩下:" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
