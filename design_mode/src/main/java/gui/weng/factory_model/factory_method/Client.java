package gui.weng.factory_model.factory_method;

public class Client {
    public static void main(String[] args) {
        AbstractFactory mySQLDBFactory = new MySQLDBFactory();
        AbstractCreateConnection connection = mySQLDBFactory.factory("test");
        connection.getConnection();
    }
}
