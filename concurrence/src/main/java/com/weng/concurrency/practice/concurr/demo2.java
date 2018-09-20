package com.weng.concurrency.practice.concurr;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 3个老师同时分发100份书卷，且最后一份试卷必须由A老师发出
 */
public class demo2 {
    // 试卷的集合
    private static List<Integer> paper = new ArrayList<>();

    // 初始化20份试卷
    public static  void  init(){
        for (int i = 0; i < 20; i++) {
            paper.add(i);
        }
    }

    // 定义分发任务
    public static synchronized void distribute(String name){
        int bound = paper.size();
        if(paper.size()==0){
            return;
        }
        if(bound==1){       // 最后一份
            if(!name.equals("A")){
                return;     // 条件不满足就结束当前任务
            }
        }
        // 随机抽取一份试卷
        Random random = new Random();
        int num = random.nextInt(bound);
        System.out.println(name+"："+paper.get(num));
        // 取走试卷
        paper.remove(num);
    }

    public static void main(String[] args) {
        init();
        Semaphore semaphore = new Semaphore(1);
        ExecutorService service = Executors.newFixedThreadPool(3);
        while (paper.size()>0){
            service.execute(()->{
                try {
                    semaphore.acquire();
                    distribute("A");
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });

            service.execute(()->{
                try {
                    semaphore.acquire();
                    distribute("B");
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            service.execute(()->{
                try {
                    semaphore.acquire();
                    distribute("C");
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();
    }
}
