package gui.weng.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyTest {

    public static void main(String[] args) {
        Person zhangsan = new Student("张三");

        // 创建一个与代理对象关联InvocationHandler
        InvocationHandler handler = new StuInvocationHandler<>(zhangsan);

        // 创建代理对象stuProxy来代理zhangsan，代理对象的每个执
        // 行方法都会替换执行Invocation中的invoke方法
        Person stuProxy = (Person) Proxy.newProxyInstance(
                zhangsan.getClass().getClassLoader(),
                zhangsan.getClass().getInterfaces(),
                handler);
        // 代理执行上交班费
        stuProxy.giveMoney();
    }
}
