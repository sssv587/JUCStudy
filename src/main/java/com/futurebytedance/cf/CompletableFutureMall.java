package com.futurebytedance.cf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author yuhang.sun
 * @version 1.0
 * @date 2022/11/6 - 22:58
 * @Description
 */
public class CompletableFutureMall {
    public static void main(String[] args) {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            return "hello 1234";
        });

//        System.out.println(completableFuture.get());
        //join()和get()作用几乎一样,就是在编译的时候不报错
        System.out.println(completableFuture.join());


//        Student student = new Student();
//        student.setId(1);
//        student.setStudentName("z3");
//        student.setMajor("cs");
//
//        student.setId(12).setStudentName("lisi").setMajor("english");
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
class Student {
    private Integer id;
    private String studentName;
    private String major;
}