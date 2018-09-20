package gui.weng.designMode.flyweight.complex_flyweight;

import gui.weng.designMode.flyweight.Flyweight;

/**
 * 具体享元角色类
 */
public class ConcreteFlyweight implements Flyweight {
    private String dataBase = null;
    /**
     * 构造函数，内蕴状态作为参数传入
     * @param dataBase
     */
    public ConcreteFlyweight(String dataBase){
        this.dataBase = dataBase;
    }

    /**
     * 外蕴状态作为参数传入方法中，改变方法的行为，
     * 但是并不改变对象的内蕴状态。
     */
    @Override
    public void excute(String state) {
        // TODO Auto-generated method stub
        System.out.println("当前的数据库：" + this.dataBase);
        System.out.println("SQL：" + state);
    }

}