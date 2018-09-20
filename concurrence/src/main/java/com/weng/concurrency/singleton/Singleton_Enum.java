package com.weng.concurrency.singleton;

import org.apache.catalina.authenticator.SingleSignOn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Singleton_Enum {

    private Singleton_Enum(){};
    public  static Singleton_Enum getInstance(){
        return Singleton.INSTANCE.getSingleton();
    }

    private  enum Singleton{
        INSTANCE;
        private Singleton_Enum singleton;

        // JVM保证这个方法绝对只调用一次
        Singleton(){
            singleton = new Singleton_Enum();
        }

        public Singleton_Enum getSingleton(){
            return singleton;
        }

    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(()->{
            Singleton_Enum instant = Singleton_Enum.getInstance();
            System.out.println(instant.hashCode());
        });
        service.execute(()->{
            Singleton_Enum instant = Singleton_Enum.getInstance();
            System.out.println(instant.hashCode());
        });
        service.execute(()->{
            Singleton_Enum instant = Singleton_Enum.getInstance();
            System.out.println(instant.hashCode());
        });
        service.execute(()->{
            Singleton_Enum instant = Singleton_Enum.getInstance();
            System.out.println(instant.hashCode());
        });
    }
}
