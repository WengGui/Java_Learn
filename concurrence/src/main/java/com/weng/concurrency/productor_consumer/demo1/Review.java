package com.weng.concurrency.productor_consumer.demo1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Review {
    private int apple = 0;

    // 模拟生产者线程
    private synchronized void producer(){
        while (apple == 10){
            try {
                wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        this.apple++;
        System.out.println("生成苹果-->" +"_________________还有"+this.apple+"个");
        notify();
    }

    // 模拟消费者线程
    private synchronized void  consumer(){
        while (apple == 0){
            try {
                wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        this.apple--;
        System.out.println("消费苹果-->" +"_________________还有"+this.apple+"个");
        notify();
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Review review = new Review();

        for (int i = 0; i < 10; i++) {
            service.execute(()->{
                review.producer();
            });

            service.execute(()->{
                review.consumer();
            });
        }
    }
}
