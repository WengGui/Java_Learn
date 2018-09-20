package com.weng.concurrency.productor_consumer.demo2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Review {
    private final static BlockingQueue<Integer> queue =new LinkedBlockingQueue<>();

    private static void producer(){
        for (int i = 0; i < 10; i++) {
            try {
                System. out .println("生产者生产的苹果编号为 : " +i+"_________________还有"+queue.size()+"个");
                queue.put(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void consumer(){
        for (int i = 0; i < 10; i++) {
            try {
                Integer num = queue.take();
                System. out .println("消费者消费的苹果编号为 : " +num+"_________________还有"+queue.size()+"个");  //放入十个苹果编号 为1到10
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);

        service.execute(()->{
            producer();
        });

        service.execute(()->{
            consumer();
        });
        service.shutdown();
    }
}
