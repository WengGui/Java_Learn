package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
 *TCP协议发送数据：
 *    1.创建发送端socket对象
 *        这一步如果成功，就说明链接已经成功了。
 *    2.获取输出流，写数据
 *    3.释放资源
 *
 *TCP一些服务器必须先开启，保证通道简历，否则直接报错。
 *java.net.ConnectException: Connection refused: connect
 */
public class Client_TCP {

    public static void main(String[] args) throws IOException{
        final Socket socket = new Socket("127.0.0.1", 10000);

        //回复服务端
        new Thread(){
            public void run() {
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                    String readline = in.readLine();
                    System.out.println(readline);

                    while(true){
                        out.println(readline);
                        System.out.println(" client send: " + readline);
                        readline = in.readLine();
                        if(readline.equals("bye"))
                            break;
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            };
        }.start();


        new Thread(){
            public void run() {
                //获得客户端发来的数据
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while(true){
                        System.out.println("Receive from server : " + reader.readLine());
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            };
        }.start();

    }
}
