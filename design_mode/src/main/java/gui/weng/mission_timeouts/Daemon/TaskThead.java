package gui.weng.mission_timeouts.Daemon;

public class TaskThead extends  Thread{

    private String name;    // 线程名
    private int time;       // 执行时间

    public TaskThead(String s, int t) {
        name = s;
        time = t;
    }

   @Override
    public void run(){
        for (int i = 0; i < time; ++i) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(name + " is interrupted when calculating, will stop...");
                return; // 注意这里如果不return的话，线程还会继续执行，所以任务超时后在这里处理结果然后返回
            }
            System.out.println("task[" + name + "] " + (i + 1) + " round");
        }
        System.out.println("task[" + name + "] finished successfully");
    }
}
