package com.weng.concurrency.synchronizeContainer;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VectorExample2 {

    public static List<Integer> vector = new Vector<>();

    public static Hashtable<String , Integer> map;

    // foreach循环
    // ConcurrentModificationException
    public static void test1(List<Integer> vector){
        for (Integer i :vector ){
            if(i == 5 ){
                vector.remove(i);
            }
        }
    }

    // 迭代器循环
    // ConcurrentModificationException
    public static void test2(List<Integer> vector){
        Iterator<Integer> iterator = vector.iterator();
        while (iterator.hasNext()){
            Integer i = iterator.next();
            if(i == 5 ){
               // vector.remove(i);
                iterator.remove();
            }
        }

    }

    // 正常
    public static void test3(List<Integer> vector){
        for (int i = 0; i < vector.size(); i++) {
            if(i == 5 ){
                vector.remove(i);
            }
        }
    }


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            vector.add(i);
        }
        test2(vector);

        for (int i = 0; i < vector.size(); i++) {
            System.out.println(vector.get(i));
        }
    }
}
