package gui.weng.mission_timeouts;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Timeout_demo3 {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        Future<?> f1 = executor.submit(() -> {
            task("A1", 3);
        });

        Future<?> f2 = executor.submit(() -> {
            task("A2", 2);
        });

        List<Future<?>> futures = new ArrayList<Future<?>>();
        futures.add(f1);
        futures.add(f2);

        try {
            if(executor.awaitTermination(3, TimeUnit.SECONDS)){
                System.out.println("task finished");
            }else{
                System.out.println("task time out,will terminate");
                for (Future<?> f : futures) {
                    if (!f.isDone()) {
                        f.cancel(true);
                    }
                }
            }
        }catch (InterruptedException e) {
            System.out.println("executor is interrupted");
        } finally {
            executor.shutdown();
        }

    }

    public static void task(String name, int time) {
        for (int i = 0; i < time; ++i) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(name
                        + " is interrupted when calculating, will stop...");
                return; // 注意这里如果不return的话，线程还会继续执行，所以任务超时后在这里处理结果然后返回
            }
            System.out.println("task[" + name + "] " + (i + 1) + " round");
        }
        System.out.println("task[" + name + "] finished successfully");
    }
}
