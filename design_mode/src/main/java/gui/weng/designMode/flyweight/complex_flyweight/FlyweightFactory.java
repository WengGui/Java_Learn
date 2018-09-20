package gui.weng.designMode.flyweight.complex_flyweight;

import gui.weng.designMode.flyweight.Flyweight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 享元工厂角色提供两种不同的方法，一种用于提供单纯享元对象，另一种用于提供复合享元对象。
 */
public class FlyweightFactory {
    private Map<String,Flyweight> files = new HashMap<String,Flyweight>();
    /**
     * 复合享元工厂方法
     */
    public Flyweight factory(List<String> compositeDataBase){
        ConcreteCompositeFlyweight compositeFly = new ConcreteCompositeFlyweight();

        for(String DataBase : compositeDataBase){
            compositeFly.add(DataBase,this.factory(DataBase));
        }

        return compositeFly;
    }
    /**
     * 单纯享元工厂方法
     */
    public Flyweight factory(String DataBase){
        //先从缓存中查找对象
        Flyweight fly = files.get(DataBase);
        if(fly == null){
            //如果对象不存在则创建一个新的Flyweight对象
            fly = new ConcreteFlyweight(DataBase);
            //把这个新的Flyweight对象添加到缓存中
            files.put(DataBase, fly);
        }
        return fly;
    }
}