package gui.weng.factory_model.simple_factory;

public class DBFactory {

    public static IConnect createConn(String DB){
        if("MySql".equalsIgnoreCase(DB)){
            IConnect myqsl = new MySql_Conn();
            myqsl.getConnection();
            return myqsl;
        }else{
            return new Oracle_Conn();
        }
    }
}
