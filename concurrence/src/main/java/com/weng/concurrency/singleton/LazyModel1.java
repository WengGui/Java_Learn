package com.weng.concurrency.singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LazyModel1 {

    private LazyModel1(){}

    private  static LazyModel1 instant = null;

    public synchronized static  LazyModel1 getInstant(){
        if( instant == null ){
            instant =  new LazyModel1();
        }
        return instant;
    }


    public static void main(String[] args) {

        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> {
            LazyModel1 instant = LazyModel1.getInstant();
            System.out.println(instant.hashCode());
        });
        service.execute(() -> {
            LazyModel1 instant = LazyModel1.getInstant();
            System.out.println(instant.hashCode());
        });
        service.execute(() -> {
            LazyModel1 instant = LazyModel1.getInstant();
            System.out.println(instant.hashCode());
        });
        service.execute(() -> {
            LazyModel1 instant = LazyModel1.getInstant();
            System.out.println(instant.hashCode());
        });
    }
}
