package com.weng.concurrency.test1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class demo1 {

    public ExecutorService executor = Executors.newFixedThreadPool(20);

    public void task1() throws InterruptedException {
        System.out.println("Hello");
//        new Thread(()->{
//            this.subTask1();
//        }).start();
        this.executor.execute(()->{
            subTask1();
        });

    }

    public void subTask1(){
        System.out.println("World....");
    }

    public void main() {
        for (int i = 0; i < 20; i++) {
            this.executor.execute(()->{
                try {
                    this.task1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void main(String[] args) {

        demo1 demo1 = new demo1();
        demo1.main();


    }

}
