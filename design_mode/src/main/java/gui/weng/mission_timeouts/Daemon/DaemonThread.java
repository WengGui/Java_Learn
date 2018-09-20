package gui.weng.mission_timeouts.Daemon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaemonThread extends  Thread{

    /**
     * 内部维护的一个任务线程表
     * 第一个key是线程运行的时间
     * value是个hashMap -> key是线程名，value是线程本体
     * (事实上这里将hashMap换成List也不错，因为这个线程名基本没用到)
     *
     * 即先将所有任务线程按照超时时间分类，在按名字分类
     */
    public Map<Integer ,HashMap<String,Thread>> threadMap = new HashMap<>();

    public void addTask(String name,int timeout,Thread th){
        HashMap<String,Thread> name2Thread = threadMap.getOrDefault(timeout,null);
        if(name2Thread==null){
            HashMap<String, Thread> map = new HashMap<>();
            map.put(name,th);
            threadMap.put(timeout,map);
        }else {
            name2Thread.put(name,th);
        }
    }


    @Override
    public void run(){
        int time = 0;
        while (true){
            try {
                time += 1000;
                Thread.sleep(900);

                HashMap<String,Thread> name2Thread = threadMap.getOrDefault(time,null);
                if(name2Thread==null){
                    System.out.println("key["+time+"] == null");
                    continue;
                }else{
                    for (Map.Entry<String,Thread> entry :name2Thread.entrySet()) {
                        Thread thread = entry.getValue();
                        thread.interrupt();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
