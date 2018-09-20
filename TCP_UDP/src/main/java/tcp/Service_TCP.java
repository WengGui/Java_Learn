package tcp;

/*
 *TCP
 *建立连接，形成传输数据的通道；
 *在连接中进行大数据量传输；
 *通过三次握手完成连接，是可靠协议；
 *必须建立连接，效率会稍低
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * TCP传输
 * Socket和ServerSocket建立客户端和服务器端建立连接后，
 * 通过Socket中的IO流进行数据的传输关闭socket同样，
 * 客户端与服务器端是两个独立的应用程序。
 */
public class Service_TCP {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(10000);
        final Socket socket = server.accept();

        new Thread(){
            public void run() {
                //获得客户端发来的数据
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while(true){
                        System.out.println("Receive from client : " + reader.readLine());
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            };
        }.start();

        new Thread(){
            public void run() {
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                    String readline = in.readLine();
                    System.out.println(readline);
                    while(true){
                        out.println(readline);
                        System.out.println(" server send: " + readline);
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

    }
}
