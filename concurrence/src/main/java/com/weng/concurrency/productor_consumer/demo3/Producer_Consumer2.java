package com.weng.concurrency.productor_consumer.demo3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Producer_Consumer2 {
    //private static BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
    private static Queue<Integer> queue = new LinkedList<>();

    private static Object lock = new Object();

    private static void producer( Semaphore ifFull, Semaphore ifEmpty, Semaphore ifConflit) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            //queue.put(i);
            ifFull.acquire();     // 确保非满
            ifConflit.acquire();  // 确保不冲突
            queue.offer(i);
            System.out.println("生产苹果-->"+i+"_________________还有"+queue.size()+"个");
            ifConflit.release();
            ifEmpty.release();
       }
    }

    private static void consumer( Semaphore ifEmpty,Semaphore ifFull, Semaphore ifConflit) throws InterruptedException {
       for (int i = 0; i < 10; i++) {
            //queue.take();
            ifEmpty.acquire();     // 确保非满
            ifConflit.acquire();  // 确保不冲突
            queue.poll();
            System.out.println("消费苹果-->" + i+"_________________还有"+queue.size()+"个");
            ifConflit.release();
            ifFull.release();
        }
    }

    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(2);
        Semaphore ifFull = new Semaphore(10);
        Semaphore ifEmpty = new Semaphore(0);
        Semaphore ifconflict = new Semaphore(1);

        Semaphore semaphore2 = new Semaphore(1);
        service.execute(()->{
            try {
                producer(ifFull,ifEmpty,ifconflict);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        service.execute(()->{
            try {
                consumer(ifEmpty,ifFull,ifconflict);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        service.shutdown();
    }
}
