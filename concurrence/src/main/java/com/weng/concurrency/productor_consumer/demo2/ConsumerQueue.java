package com.weng.concurrency.productor_consumer.demo2;

import java.util.concurrent.BlockingQueue;

public class ConsumerQueue implements Runnable {
    private final BlockingQueue conQueue;

    public ConsumerQueue(BlockingQueue conQueue)
    {
        this .conQueue =conQueue;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        for (int i=0;i<10;i++)
        {
            try {
                System. out .println("消费者消费的苹果编号为 ：" +conQueue .take()+"_________________还有"+conQueue.size()+"个");
                //Thread. sleep(1000);  //在这里sleep是为了看的更加清楚些

            } catch (InterruptedException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }
}
