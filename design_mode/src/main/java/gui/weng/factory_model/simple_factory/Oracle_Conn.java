package gui.weng.factory_model.simple_factory;

public class Oracle_Conn implements IConnect{

    public boolean getConnection() {
        // 获取数据库的配置文件
        String configPath = this.getClass().getResource("Oracle.properties").getPath();
        // todo..  具体的创建连接逻辑
        System.out.println("Oracle连接");
        return true;
    }
}
