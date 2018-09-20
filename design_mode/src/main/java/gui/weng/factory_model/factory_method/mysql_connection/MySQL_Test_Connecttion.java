package gui.weng.factory_model.factory_method.mysql_connection;

import gui.weng.factory_model.factory_method.AbstractCreateConnection;

public class MySQL_Test_Connecttion implements AbstractCreateConnection {
    @Override
    public boolean getConnection() {
        System.out.println("MySql的测试环境");
        return false;
    }
}
