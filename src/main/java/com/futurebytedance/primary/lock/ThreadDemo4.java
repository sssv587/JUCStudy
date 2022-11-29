package com.futurebytedance.primary.lock;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/29 - 23:32
 * @Description list集合线程不安全
 */
public class ThreadDemo4 {
    public static void main(String[] args) {
        //ArrayList集合
//        List<String> list = new ArrayList<>();

        //Vector解决
//        List<String> list = new Vector<>();

        //使用Collections解决
//        List<String> list = Collections.synchronizedList(new ArrayList<>());

        //CopyOnWriteArrayList解决
        List<String> list = new CopyOnWriteArrayList<>();

        //java.util.ConcurrentModificationException
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                //向集合添加内容
                list.add(UUID.randomUUID().toString().substring(0, 8));
                //从集合获取内容
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
