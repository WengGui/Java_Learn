package gui.weng.factory_model.simple_factory;

public class Client {

    public static void main(String[] args) {
        IConnect mySql = DBFactory.createConn("MySql");
        mySql.getConnection();
    }
}
