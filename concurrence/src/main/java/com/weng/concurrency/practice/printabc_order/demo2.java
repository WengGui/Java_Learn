package com.weng.concurrency.practice.printabc_order;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class demo2 {
    // 以A开始的信号量,初始信号量数量为1
    private static Semaphore semaphoreA = new Semaphore(1);
    // B、C信号量,A完成后开始,初始信号数量为0
    private static Semaphore semaphoreB = new Semaphore(0);
    private static Semaphore semaphoreC = new Semaphore(0);

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

        service.execute(()->{
            print(semaphoreB,semaphoreC,"B");
        });


        service.execute(()->{
            print(semaphoreC,semaphoreA,"C");
        });

        service.execute(()->{
            print(semaphoreA,semaphoreB,"A");
        });
        service.shutdown();
    }


    public static void print(Semaphore curr,Semaphore next , String ch){
        try{
            for (int i = 0; i < 6; i++) {
                curr.acquire();
                System.out.print(ch);
                next.release();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
