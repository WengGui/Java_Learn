package com.weng.concurrency.productor_consumer.demo2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class PublicBoxQueue {
    public static void main(String[] args) {

        BlockingQueue publicBoxQueue= new LinkedBlockingQueue(5);

        ExecutorService service = Executors.newFixedThreadPool(2);
        service.execute(new ProducerQueue(publicBoxQueue));
        service.execute(new ConsumerQueue(publicBoxQueue));
        service.shutdown();
//        Thread producer = new Thread(new ProducerQueue(publicBoxQueue));
//        Thread consumer = new Thread(new ConsumerQueue(publicBoxQueue));
//
//        producer.start();
//        consumer.start();

    }

}
