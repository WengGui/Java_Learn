package com.weng.concurrency.Syc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SyncExample2 {

    private int count = 0;

    public void test1(int num) {
        for (int i = 0; i < 10; i++) {
            System.out.println("Thread_"+num+":"+i);
        }
    }

    // 修饰一个类
    public void test2(int num){
        synchronized(SyncExample2.class){
            for (int i = 0; i < 10; i++) {
                System.out.println("Thread_"+num+":"+i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 修饰一个静态方法
     * 等同于test2
     * @param num
     */
    // public static synchronized void test3(int num){
    public synchronized static void test3(int num){
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
        SyncExample2 syncExample1 = new SyncExample2();
        SyncExample2 syncExample2 = new SyncExample2();
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(()->{
            syncExample1.test3(1);
        });
        service.execute(()->{
            syncExample2.test3(2);
        });
    }
}
