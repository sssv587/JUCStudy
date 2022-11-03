package com.futurebytedance.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ThreadBaseDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {

        }, "t1");
        t1.start();

    }
}

// java = C++ ---》  (C++)-- = java