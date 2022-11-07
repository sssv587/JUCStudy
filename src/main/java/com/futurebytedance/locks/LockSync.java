package com.futurebytedance.locks;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/8 - 0:45
 * @Description synchronized字节码与原语分析
 * synchronized字节码分析
 * 1.同步代码块
 *  javap -c .\LockSync.class(m1方法)
 *    public void m1();
 *     Code:
 *        0: aload_0
 *        1: getfield      #3                  // Field object:Ljava/lang/Object;
 *        4: dup
 *        5: astore_1
 *        6: monitorenter
 *        7: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;
 *       10: ldc           #5                  // String ---hello synchronized lock
 *       12: invokevirtual #6                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
 *       15: aload_1
 *       16: monitorexit
 *       17: goto          25
 *       20: astore_2
 *       21: aload_1
 *       22: monitorexit
 *       23: aload_2
 *  有两个monitorexit，为了保证非正常情况也能够释放锁
 *
 *  一般情况就是一个enter对应两个exit
 *  极端情况:m1方法内添加一个异常就会只有一个enter和一个exit
 *
 * 2.普通同步方法
 *   javap -v .\LockSync.class(m2方法)
 *
 *   public synchronized void m2();
 *     descriptor: ()V
 *     flags: ACC_PUBLIC, ACC_SYNCHRONIZED
 *   调用指令将会检查方法的ACC_SYNCHRONIZED访问标志是否被设置。
 *   如果设置了，执行线程会将现持有monitor锁，然后再执行方法，
 *   最后在方法完成(无论是正常完成还是非正常完成)时释放monitor
 *
 *  3.静态同步方法
 *   public static synchronized void m3();
 *     descriptor: ()V
 *     flags: ACC_PUBLIC, ACC_STATIC, ACC_SYNCHRONIZED
 *   ACC_STATIC, ACC_SYNCHRONIZED访问标识区分该方法是否静态同步方法
 *
 *
 *  什么是管程monitor?
 *  原语分析:
 *  ObjectMonitor.java -> ObjectMonitor.cpp -> ObjectMonitor.hpp
 *  ObjectMonitor.hpp
 *  每个对象天生都带着一个对象监视器
 *  每一个被锁住的对象都会和Monitor关联起来
 *
 *  ObjectMonitor中有几个关键属性：
 *  1._owner:指向持有ObjectMonitor对象的线程
 *  2._WaitSet:存放处于wait状态的线程队列
 *  3._EntryList:存放处于等待锁block状态的线程队列
 *  4._recursions:锁的重入次数
 *  5._count:用来记录线程获取锁的次数
 */
public class LockSync {
    Object object = new Object();
    Book b1 = new Book();

    public void m1(){
        synchronized (object){
            System.out.println("---hello synchronized lock");
            throw new RuntimeException("---exp");
        }
    }

    public synchronized void m2(){
        System.out.println("---hello synchronized lock m2");
    }

    public static synchronized void m3(){
        System.out.println("---hello synchronized lock m2");
    }


    public static void main(String[] args) {

    }
}

class Book extends Object{
    //java = (C++) --
}
