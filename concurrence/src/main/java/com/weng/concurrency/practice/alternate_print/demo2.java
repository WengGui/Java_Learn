package com.weng.concurrency.practice.alternate_print;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;

public class demo2 {

    private volatile static int count=1;

    /**
     * 打印数字
     */
    private static void task1(){
        System.out.print(count*2-1);
        System.out.print(count*2);

    }

    /**
     * 打印字母
     */
    private static void task2(){
        System.out.print((char)(count+'A'-1));
        count++;
    }

    public static void main(String[] args) {
        Semaphore semaphore1 = new Semaphore(1);
        Semaphore semaphore2 = new Semaphore(0);

        ExecutorService service = Executors.newFixedThreadPool(2);

        Executors.newSingleThreadExecutor();

        for (int i = 0; i < 26; i++) {
            service.execute(()->{
                try {
                    semaphore1.acquire();
                    task1();
                    semaphore2.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            service.execute(()->{
                try {
                    semaphore2.acquire();
                    task2();
                    semaphore1.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();
    }
}
