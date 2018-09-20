package gui.weng.designMode.flyweight.complex_flyweight;

import gui.weng.designMode.flyweight.Flyweight;

import java.util.ArrayList;
import java.util.List;

public class Client {

    public static void main(String[] args) {
        List<String> compositeState = new ArrayList<String>();
        compositeState.add("MySql");
        compositeState.add("Oracle");
        compositeState.add("SQLServer");
        compositeState.add("MySql");
        compositeState.add("Oracle");

        FlyweightFactory flyFactory = new FlyweightFactory();
        Flyweight compositeFly1 = flyFactory.factory(compositeState);
        Flyweight compositeFly2 = flyFactory.factory(compositeState);
        compositeFly1.excute("select * from tb");

        System.out.println("---------------------------------");
        System.out.println("复合享元模式是否可以共享对象：" + (compositeFly1 == compositeFly2));

        String state = "MySql";
        Flyweight fly1 = flyFactory.factory(state);
        Flyweight fly2 = flyFactory.factory(state);
        System.out.println("单纯享元模式是否可以共享对象：" + (fly1 == fly2));
    }
}