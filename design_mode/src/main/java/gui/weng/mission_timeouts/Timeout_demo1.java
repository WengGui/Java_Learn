package gui.weng.mission_timeouts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 超时任务取消
 *
 */
public class Timeout_demo1 {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("go..........");

        // 定义一个任务（执行的时间约为10s）
        Thread t1 = new Thread(()->{
            task("A",10);
        });
        t1.start();

        // 阻塞主线程3秒，3后就会形成主线程和任务线程并行的情况
        // 相当于给任务3秒钟执行时间
        try {
            t1.join(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 干掉任务线程，实现任务超时取消
        // 如果这是任务已经结束了，那执行这句也没什么影响
        t1.interrupt();

        System.out.println("end");

    }


    private static void task(String name ,int times){
        for (int i = 0; i < times; ++i) {
            System.out.println("task:[" + name + "]" + (i + 1) + " round");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Task["+name + "] is interrupted when calculating, will stop...");
                return; // 注意这里如果不return的话，线程还会继续执行，所以任务超时后在这里处理结果然后返回
            }
        }
    }
}
