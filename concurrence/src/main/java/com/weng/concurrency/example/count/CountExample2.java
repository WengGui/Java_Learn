package com.weng.concurrency.example.count;

import com.weng.concurrency.annoations.NotThreadSafa;
import com.weng.concurrency.annoations.ThreadSafa;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafa
public class CountExample2 {
    // 请求总数
    public static int clientTotal = 5000;
    // 同时并发执行的线程数
    public static int threadTotal = 200;

    public static AtomicInteger count = new AtomicInteger(0);


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal); //同时并发执行的线程数
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for(int i = 0 ; i < clientTotal ; i++){
            executorService.execute( ()->{
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (Exception e) {
                    System.out.println("Error:"+e);
                }
                countDownLatch.countDown();
            } );
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("count:"+count);
    }

    private static void add(){
        count.incrementAndGet();
        //count.getAndIncrement();
    }

}
