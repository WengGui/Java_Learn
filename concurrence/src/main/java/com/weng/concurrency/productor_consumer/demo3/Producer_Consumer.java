package com.weng.concurrency.productor_consumer.demo3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

public class Producer_Consumer {
    //private static BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
    private static Queue<Integer> queue = new LinkedList<>();

    private static Semaphore full = new Semaphore(0);
    private static Semaphore empty = new Semaphore(10);
    private static Semaphore muxtex = new Semaphore(1);

    private static void producer(Semaphore full,Semaphore empty,Semaphore muxtex){
        for (int i = 0; i < 10; i++) {
            int num = i;
            try {
                empty.acquire();    // 有空格子放面包吗
                muxtex.acquire();   // 有人和我抢着放面包吗
                queue.offer(num);   // 放个面包
                System.out.println("生产面包，编号为"+num+"_______________现在有"+queue.size()+"个面包了");
                muxtex.release();
                full.release();     // 面包数+1
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void consumer(Semaphore full,Semaphore empty,Semaphore muxtex){
        for (int i = 0; i < 10; i++) {
            try {
                full.acquire();     // 有面包吗
                muxtex.acquire();   // 有谁跟我抢吗
                Integer num = queue.poll();
                System.out.println("吃了面包，编号为"+num+"_______________还剩下"+queue.size()+"个面包了");
                muxtex.release();
                empty.release();    // 多一个空格子
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void consumer2(Semaphore full,Semaphore empty,Semaphore muxtex){
        for (int i = 0; i < 10; i++) {
            try {
                full.acquire(2);     // 这是个胖子，一次要吃俩
                muxtex.acquire();            // 有谁跟我抢吗
                Integer num1 = queue.poll();
                Integer num2 = queue.poll();
                System.out.println("吃了面包，编号为"+num1+"和"+num2+"_______________还剩下"+queue.size()+"个面包了");
                muxtex.release();
                empty.release(2);    // 多两个空格子
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);

        service.execute(()->{
            producer(full,empty,muxtex);
        });

        service.execute(()->{
            consumer2(full,empty,muxtex);
        });

        service.shutdown();
    }
}
