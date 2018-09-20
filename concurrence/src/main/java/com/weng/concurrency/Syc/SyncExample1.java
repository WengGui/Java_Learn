package com.weng.concurrency.Syc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SyncExample1 {

    private int count = 0;

    public void test1(int num) {
        for (int i = 0; i < 10; i++) {
            System.out.println("Thread_"+num+":"+i);
        }
    }

    public void test2(int num){
        synchronized(this){
            for (int i = 0; i < 10; i++) {
                System.out.println("Thread_"+num+":"+i);
            }
        }
    }

    /**
     * 等同于test2 作用方法
     * 同一个对象 多选线程跑 是 没问题的
     * 不同对象 多线程跑 就有问题啦
     * @param num
     */
    public synchronized void test3(int num){
        for (int i = 0; i < 10; i++) {
            System.out.println("Thread_"+num+":"+i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        SyncExample1 syncExample1 = new SyncExample1();
        SyncExample1 syncExample2 = new SyncExample1();
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(()->{
            syncExample1.test3(1);
        });
        service.execute(()->{
            syncExample2.test3(2);
        });
    }
}
