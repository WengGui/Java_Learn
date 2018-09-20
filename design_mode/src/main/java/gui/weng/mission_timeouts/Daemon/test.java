package gui.weng.mission_timeouts.Daemon;

public class test {

    public static void main(String[] args) throws InterruptedException {
        Thread task1 = new Thread(new TaskThead("A",10));
        task1.setDaemon(true);
        task1.start();

        Thread.sleep(3000);
        System.out.println("主线程结束");
    }
}
