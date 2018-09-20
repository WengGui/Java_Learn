package gui.weng.factory_model.simple_factory;

public class MySql_Conn implements IConnect{

    public  MySql_Conn(){};
    public boolean getConnection() {
        // 获取数据库的配置文件
        String configPath = this.getClass().getResource("Mysql.properties").getPath();
        // todo..  具体的创建连接逻辑
        System.out.println("MySQL连接");
        return true;
    }
}
