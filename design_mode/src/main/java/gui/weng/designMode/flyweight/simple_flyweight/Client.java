package gui.weng.designMode.flyweight.simple_flyweight;

import gui.weng.designMode.flyweight.Flyweight;

public class Client {

    public static void main(String[] args) {

        // 创建一个工厂类
        FlyweightFactory factory = new FlyweightFactory();

        // 创建一个mysql连接(假装创建了数据库连接)
        Flyweight mySQL1 = factory.factory("MySQL");
        System.out.println(mySQL1);
        mySQL1.excute("select * from tb_mysql");

        System.out.println("---------------------------------------------");
        // 创建一个oracle连接(假装创建了数据库连接)
        Flyweight oracle = factory.factory("Oracle");
        System.out.println(oracle);
        mySQL1.excute("select * from tb_oracle");
        System.out.println("---------------------------------------------");
        // 试图在创建一个mysql连接
        Flyweight mySQL2 = factory.factory("MySQL");
        System.out.println(mySQL2);
        mySQL2.excute("delete * from tb_mysql");
    }
}