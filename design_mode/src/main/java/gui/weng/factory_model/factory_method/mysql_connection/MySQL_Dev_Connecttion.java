package gui.weng.factory_model.factory_method.mysql_connection;

import gui.weng.factory_model.factory_method.AbstractCreateConnection;

public class MySQL_Dev_Connecttion implements AbstractCreateConnection {
    @Override
    public boolean getConnection() {
        System.out.println("MySql的开发环境");
        return false;
    }
}
