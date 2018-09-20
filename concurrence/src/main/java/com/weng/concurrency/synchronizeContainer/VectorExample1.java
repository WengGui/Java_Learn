package com.weng.concurrency.synchronizeContainer;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VectorExample1 {

    public static List<Integer> vector = new Vector<>();

    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < 50; i++) {
                vector.add(i);
            }


            ExecutorService service = Executors.newCachedThreadPool();

            service.execute(() -> {
                for (int i = 0; i < vector.size(); i++) {
                    vector.remove(i);
                }
            });

            service.execute(() -> {
                for (int i = 0; i < vector.size(); i++) {
                    vector.get(i);
                }
            });

        }
    }
}
