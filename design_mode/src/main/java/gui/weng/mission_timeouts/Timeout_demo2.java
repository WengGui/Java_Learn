package gui.weng.mission_timeouts;

import java.util.concurrent.*;

public class Timeout_demo2 {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Boolean> f1 = service.submit(()->{
           return task("A",10);
        });

        try {
            // future将在2秒之后取结果
            // 如果这是任务未跑完的话，会抛出TimeoutException
            if (f1.get(2, TimeUnit.SECONDS)) {
                System.out.println("one complete successfully");
            }
        } catch (InterruptedException e) {
            System.out.println("future在睡着时被打断");
            service.shutdownNow();
        } catch (ExecutionException e) {
            System.out.println("future在尝试取得任务结果时出错");
            service.shutdownNow();
        } catch (TimeoutException e) {
            System.out.println("future时间超时");
            f1.cancel(true);
            // executor.shutdownNow();
            // executor.shutdown();
        } finally {
            service.shutdownNow();
        }


    }

    private static boolean task(String name, int time){
        for (int i = 0; i < time; ++i) {
            System.out.println("task:[" + name + "]" + (i + 1) + " round");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Task["+name + "] is interrupted when calculating, will stop...");
                return false; // 注意这里如果不return的话，线程还会继续执行，所以任务超时后在这里处理结果然后返回
            }
        }
        return  true;
    }
}
