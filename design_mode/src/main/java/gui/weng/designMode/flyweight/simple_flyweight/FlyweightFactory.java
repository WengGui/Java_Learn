package gui.weng.designMode.flyweight.simple_flyweight;

import gui.weng.designMode.flyweight.Flyweight;

import java.util.HashMap;
import java.util.Map;

public class FlyweightFactory {
    private Map<String,Flyweight> files = new HashMap<String,Flyweight>();

    public Flyweight factory(String dataBase){
        //先从缓存中查找是否有该数据库连接
        Flyweight fly = files.get(dataBase);
        if(fly == null){
            //如果对象不存在则创建一个新的数据库连接
            fly = new ConcreteFlyweight(dataBase);
            //把这个新的数据库连接对象添加到缓存中
            files.put(dataBase, fly);
        }
        return fly;
    }
}