package gui.weng.mission_timeouts.Daemon;

import java.util.HashMap;

public class Client {
    public static void main(String[] args) {

        // 创建多个任务
        Thread task1 = new Thread(new TaskThead("A",10));
        Thread task2 = new Thread(new TaskThead("B",8));
        Thread task3 = new Thread(new TaskThead("C",2));
        Thread task4 = new Thread(new TaskThead("D",7));
        Thread task5 = new Thread(new TaskThead("E",6));
        Thread task6 = new Thread(new TaskThead("F",5));

        DaemonThread daemon = new DaemonThread();
        daemon.setDaemon(true);
        // 将任务加入到守护进程中,并设置超时时间
        daemon.addTask("A",5000,task1);
        daemon.addTask("B",5000,task2);
        daemon.addTask("C",4000,task3);
        daemon.addTask("D",2000,task4);
        daemon.addTask("E",3000,task5);
        daemon.addTask("F",4000,task6);

        task1.start();
        task2.start();
        task3.start();
        task4.start();
        task5.start();
        task6.start();

        daemon.start();


    }
}
