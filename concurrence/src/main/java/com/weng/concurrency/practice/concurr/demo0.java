package com.weng.concurrency.practice.concurr;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class demo0{
    private static volatile List<Integer> paper = new ArrayList<>();

    public static  void  init(){
        for (int i = 0; i < 10; i++) {
            paper.add(i);
        }
    }

    public synchronized static void distribute(String name){

        int bound = paper.size();
        if(paper.size()==0){
            return;
        }
        if(bound==1){       // 最后一份
            if(!name.equals("A")){
                return;
            }
        }
        Random random = new Random();
        int num = random.nextInt(bound);
        System.out.println(name+"："+paper.get(num));
        paper.remove(num);
    }

    public static void main(String[] args) {
        init();

        ExecutorService service = Executors.newFixedThreadPool(3);
        while (paper.size()>0){
            service.execute(()->{
                distribute("A");
            });

            service.execute(()->{
                distribute("B");
            });

            service.execute(()->{
                distribute("C");
            });
        }
        service.shutdown();
    }
}
