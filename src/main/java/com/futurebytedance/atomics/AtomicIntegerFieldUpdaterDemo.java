package com.futurebytedance.atomics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/20 - 18:03
 * @Description 对象的属性修改原子类
 * 以一种线程安全的方式操作非线程安全对象的某些字段。
 * 需求：
 * 10个线程
 * 每个线程转账1000
 * 不使用synchronized,尝试使用AtomicIntegerFieldUpdater来实现
 * <p>
 * 1000个人同时向一个账号转账一元钱，那么累计应该增加1000元，
 * 除了synchronized和CAS,还可以使用AtomicIntegerFieldUpdater来实现。
 */
public class AtomicIntegerFieldUpdaterDemo {
    public static void main(String[] args) throws InterruptedException {
        BankAccount bankAccount = new BankAccount();
        CountDownLatch countDownLatch = new CountDownLatch(10);

        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                try {
                    for (int j = 1; j <= 1000; j++) {
//                        bankAccount.add();
                        bankAccount.transferMoney(bankAccount);
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }, String.valueOf(i)).start();
        }

        countDownLatch.await();

        System.out.println(Thread.currentThread().getName() + "\t" + "result:" + bankAccount.money);
    }
}

class BankAccount { //资源类
    String bankName = "CCB";
    //更新的对象必须使用public volatile修饰符
    public volatile int money = 0;

//    int money = 0; //钱数

    public synchronized void add() {
        money++;
    }

    //因为对象的属性修改类型原子类都是抽象类，所以每次使用都必须
    //使用静态方法newUpdater()创建一个更新器，并且需要设置想要更新的类和属性。
    static final AtomicIntegerFieldUpdater<BankAccount> fieldUpdater =
            AtomicIntegerFieldUpdater.newUpdater(BankAccount.class, "money");

    //不加synchronized,保证高性能原子性,局部微创小手术
    public void transferMoney(BankAccount bankAccount) {
        fieldUpdater.getAndIncrement(bankAccount);
    }
}