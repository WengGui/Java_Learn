package com.weng.concurrency.singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LazyModel3 {
    private volatile static LazyModel3 instant = null;

    private LazyModel3(){}

    public static LazyModel3 getInstant(){
        if(instant == null){
            synchronized (LazyModel3.class){
                if(instant == null){
                    return new LazyModel3();
                }
            }
        }
        return instant;
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> {
            LazyModel3 instant = LazyModel3.getInstant();
            System.out.println(instant.hashCode());
        });
        service.execute(() -> {
            LazyModel3 instant = LazyModel3.getInstant();
            System.out.println(instant.hashCode());
        });
        service.execute(() -> {
            LazyModel3 instant = LazyModel3.getInstant();
            System.out.println(instant.hashCode());
        });
        service.execute(() -> {
            LazyModel3 instant = LazyModel3.getInstant();
            System.out.println(instant.hashCode());
        });
    }
}
