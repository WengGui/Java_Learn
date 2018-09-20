package com.weng.concurrency.practice.concurr;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class demo1 {
    private static ConcurrentMap<Integer,Integer> testPager = new ConcurrentHashMap<>();
    private static Object lock=new Object();

    public static void init(){
        for (int i = 0; i < 10; i++) {
            Integer num = i;
            testPager.put(i,1);
        }
    }

    public static void distributePaper(String teacherName){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(testPager.size()==0){
            return;
        }
        Random random = new Random();
        Integer num = random.nextInt(10);
        synchronized(lock){
            if(testPager.getOrDefault(num,-1)!=-1){
                testPager.remove(num);
                System.out.println("老师" + teacherName + ":发出试卷" + num);
            }
        }
    }


    public static void main(String[] args) {
        init();

        ExecutorService service = Executors.newFixedThreadPool(3);
        while (testPager.size()>0){
            service.execute(()->{
                distributePaper("A");
            });

            service.execute(()->{
                distributePaper("B");
            });

            service.execute(()->{
                distributePaper("C");
            });
        }
        service.shutdown();
    }
}
