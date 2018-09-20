package tcp.demo1;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MyTCPClient {

    public static void main(String[] args) throws  Exception{
        final Socket socket = new Socket("10.132.192.192", 10000);

        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> {
            sender(socket);
        });

        service.execute(() -> {
            receiver(socket);
        });

        service.shutdown();

    }

    private static void sender(Socket socket){
        try{
            System.out.println("客户端已启动，请输入");
            Scanner scanner =null;

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            boolean flag = true;
            while(flag){
                scanner = new Scanner(System.in);
                String msg = scanner.nextLine();
                if(msg.equals("bye")){
                    out.println(msg);
                    flag = false;
                }
                out.println(msg);
                System.out.println("客户端:["+msg+"]");
            }
            scanner.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void receiver(Socket socket){
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
            boolean flag = true;
            while (flag){
                String msg = reader.readLine();
                System.out.println("服务端:"+ msg);
                if(msg.contains("bye")){
                    System.out.println("客户端收到byebye了");
                    flag = false;
                }
            }
            inputStream.close();
            reader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //System.exit(0);
        }
    }
}


