package gui.weng.dynamic_proxy;

/**
 * 再定义一个检测方法执行时间的工具类，在任何方法执行前先调用start方法，
 * 执行后调用finsh方法，就可以计算出该方法的运行时间，
 */
public class MonitorUtil {
    private static ThreadLocal<Long> t1 = new ThreadLocal<>();

    public static void start(){
        t1.set(System.currentTimeMillis());
    }

    public static void finish(String methodName){
        long finish = System.currentTimeMillis();
        long span = finish-t1.get();
        System.out.println(methodName+" 耗时："+span+"ms");
    }
}
