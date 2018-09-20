package com.weng.concurrency.singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HungryModel1 {

    private static HungryModel1 instance = new HungryModel1();

    private HungryModel1(){};

    public static HungryModel1 getInstance(){
        return instance;
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(()->{
            HungryModel1 instant = HungryModel1.getInstance();
            System.out.println(instant.hashCode());
        });
        service.execute(()->{
            HungryModel1 instant = HungryModel1.getInstance();
            System.out.println(instant.hashCode());
        });
        service.execute(()->{
            HungryModel1 instant = HungryModel1.getInstance();
            System.out.println(instant.hashCode());
        });
        service.execute(()->{
            HungryModel1 instant = HungryModel1.getInstance();
            System.out.println(instant.hashCode());
        });
    }
}
