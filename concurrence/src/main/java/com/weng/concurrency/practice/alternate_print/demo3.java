package com.weng.concurrency.practice.alternate_print;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class demo3 {
    private static int count=1;

    private static void task(String name){
        long id = Thread.currentThread().getId();
        System.out.print("currentThread:"+name+" --> ");
        for (int i = 0; i < 3; i++) {
            System.out.print(count++ +" ,");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Semaphore semaphoreA = new Semaphore(1);
        Semaphore semaphoreB = new Semaphore(0);
        Semaphore semaphoreC = new Semaphore(0);

        ExecutorService service = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 11; i++) {
            service.execute(()->{
                try {
                    semaphoreA.acquire();
                    task("A");
                    semaphoreB.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            service.execute(()->{
                try {
                    semaphoreB.acquire();
                    task("B");
                    semaphoreC.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            service.execute(()->{
                try {
                    semaphoreC.acquire();
                    task("C");
                    semaphoreA.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();
    }
}
