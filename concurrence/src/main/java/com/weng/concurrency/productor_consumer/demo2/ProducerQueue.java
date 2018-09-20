package com.weng.concurrency.productor_consumer.demo2;

import java.util.concurrent.BlockingQueue;

public class ProducerQueue implements Runnable {

    private final BlockingQueue proQueue;

    public ProducerQueue(BlockingQueue proQueue) {
        this .proQueue =proQueue;
    }

    @Override
    public void run() {
        for (int i=0;i<10;i++) {
            try {
                proQueue.put(i);
                System. out .println("生产者生产的苹果编号为 : " +i+"_________________还有"+proQueue.size()+"个");  //放入十个苹果编号 为1到10
                //Thread.sleep(2000);
            } catch (InterruptedException  e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
    }
}
