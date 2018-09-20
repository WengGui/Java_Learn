package com.weng.concurrency.practice.alternate_print;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 两个线程交替循环执行
 *   线程一执行两次
 *   线程二执行两次
 * 重复上述两个过程10次
 */
public class demo1 {

    private volatile static Map<Long,Integer> totalThreadNum = new HashMap<>();

    private static void task(String string){
        Long id = Thread.currentThread().getId();
        if(totalThreadNum.getOrDefault(id,-1)==-1){
            totalThreadNum.put(id,1);
        }
        for (int i = 0; i < 2; i++) {
            System.out.println(id+": "+string);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore1 = new Semaphore(1);
        Semaphore semaphore2 = new Semaphore(0);
        CountDownLatch countDownLatch = new CountDownLatch(20);
        ExecutorService service1 = Executors.newScheduledThreadPool(2);
        ExecutorService service = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 10; i++) {
            service.execute(()->{
                try {
                    semaphore1.acquire();
                    task("sub");
                    semaphore2.release();
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            service.execute(()->{
                try {
                    semaphore2.acquire();
                    task("main");
                    semaphore1.release();
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.await();
        System.out.println("totalThreadNum:"+totalThreadNum.size());
        service.shutdown();
    }
}
