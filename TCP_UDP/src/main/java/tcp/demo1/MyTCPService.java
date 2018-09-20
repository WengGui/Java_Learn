package tcp.demo1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务端代码
 *
 * 服务端就干一个活 》》》 把收到的信息原路返回
 */
public class MyTCPService {

    public static void main(String[] args) throws  Exception{

        // 设置服务端就监听本机的某端口
        ServerSocket server = new ServerSocket(10000);
        System.out.println("服务端已启动");

        // 服务端需要不停的监听客户端的连接，所以这里要写一个死循环
        while (true){
            // server.accept() 会堵塞主线程，直到收到客户端的连接才会继续下去
            final Socket socket = server.accept();
            String hostAddress = socket.getInetAddress().getHostAddress();  // 获取客户端的IP地址
            System.out.println("["+hostAddress+"]已连接");

            // 开启一个线程池，由于要实现与多客户端通讯，所以必须使用线程池
            // 来一个客户就开一个线程专门为其服务
            // 此时将会一个主线程(负责不断监听请求)，和0~N个任务线程
            ExecutorService service = Executors.newCachedThreadPool();

            service.execute(()->{
                receive(socket);    // 提交线程任务
            });

            // 这个是为了实现客户端和服务端对话用
            // 一个线程接收客户端信息，一个线程接收用户的输入，从而实现对话的
            // 但是考虑到命令行下是单线程的，无法实现与多客户端对话，自己有懒得去写GUI，故嘿嘿..
            // 取消注释能再命令行实现与单个客户端对话
//          service.execute(()->{
//              sender(socket);
//          });
        }
    }

    private static void receive(Socket socket){
        try {
            InputStream inputStream = socket.getInputStream();
            // 将客户端发来的信息装在BufferedReader中
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
            String hostAddress = socket.getInetAddress().getHostAddress();
            while (true){
                // 判断客户端是否断开连接，比如在Linux上按 ctr+c 退出进程
                if(socket.isClosed()){
                    System.out.println("["+socket.getInetAddress().getHostAddress()+"]已断开连接");
                    //break;
                    return;     // 退出当前线程
                }

                // 读取客户端的来的信息，这儿会堵塞线程
                String msg = reader.readLine();
                String receiveMsg = ("["+hostAddress+"]:"+msg).toString().trim();
                System.out.println(receiveMsg);

                // 用PrintWriter装饰socket的OutputStream
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                // 给客户端发送信息
                out.println(receiveMsg);
                System.out.println("服务端:"+receiveMsg);
                // 检测关键字
                if(msg.equals("bye")){
                    System.out.println("["+socket.getInetAddress().getHostAddress()+"]再见");
                    return;     // 退出当前线程
                }
            }
        } catch (Exception e) {
            if(socket.isClosed()){
                System.out.println("["+socket.getInetAddress().getHostAddress()+"]socket关闭");
            }
            if(socket.isConnected()){
                System.out.println("["+socket.getInetAddress().getHostAddress()+"]已断开连接");
            }
            return;
        }
    }

//    private static void sender(Socket socket){
//        try {
//            System.out.println("服务端已启动，请输入");
//            Scanner scanner = new Scanner(System.in);
//            String msg = scanner.nextLine();
//
//            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//            while(true){
//                out.println(msg);
//                System.out.println("服务端:["+msg+"]");
//                msg = scanner.nextLine();
//                if(msg.equals("bye"))
//                    break;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
