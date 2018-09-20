package gui.weng.factory_model.factory_method;

import gui.weng.factory_model.factory_method.mysql_connection.MySQL_Dev_Connecttion;
import gui.weng.factory_model.factory_method.mysql_connection.MySQL_Test_Connecttion;

public class MySQLDBFactory implements AbstractFactory {
    @Override
    public AbstractCreateConnection factory(String type) {
        // 创建测试环境的连接 这里就写两个啦 不想写多
        if("test".equalsIgnoreCase(type)){
            return new MySQL_Test_Connecttion();
        }else if("dev".equalsIgnoreCase(type)){
            return new MySQL_Dev_Connecttion();
        }else {
            return null;
        }
    }
}
