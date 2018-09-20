package tcp.demo1;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.*;

public class MyTCPClient2 {

    // 客户端的主线程，就干了三件事
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        // 1、创建TCP连接
        final Socket socket = new Socket("192.168.0.142", 10000);

        // 2、开启任务线程
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Boolean> sender = service.submit(new Sender(socket));
        Future<Boolean> receive = service.submit(new Receiver(socket));

        // 3、关闭线程池，结束程式
        if(!sender.get() && !receive.get()){
            System.out.println("Sender Over");
            System.out.println("Receive Over");
            service.shutdown();
        }
    }
}

class Sender implements Callable<Boolean> {
    private Socket socket = null;

    public Sender(Socket socket){
        this.socket = socket;
    }

    @Override
    public Boolean call() throws Exception {
        try{
            System.out.println("客户端已启动，请输入");
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            boolean flag = true;
            Scanner scanner = null;
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
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}


class Receiver implements Callable<Boolean>{

    private Socket socket = null;

    public Receiver(Socket socket){
        this.socket = socket;
    }


    @Override
    public Boolean call() throws Exception {
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
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}