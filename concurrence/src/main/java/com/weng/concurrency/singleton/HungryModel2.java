package com.weng.concurrency.singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HungryModel2 {

    private static HungryModel2 instance = null;

    static {
        if(instance == null){
            instance = new HungryModel2();
        }
    }

    private HungryModel2(){};

    public static HungryModel2 getInstance(){
        return instance;
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(()->{
            HungryModel2 instant = HungryModel2.getInstance();
            System.out.println(instant.hashCode());
        });
        service.execute(()->{
            HungryModel2 instant = HungryModel2.getInstance();
            System.out.println(instant.hashCode());
        });
        service.execute(()->{
            HungryModel2 instant = HungryModel2.getInstance();
            System.out.println(instant.hashCode());
        });
        service.execute(()->{
            HungryModel2 instant = HungryModel2.getInstance();
            System.out.println(instant.hashCode());
        });
    }
}
