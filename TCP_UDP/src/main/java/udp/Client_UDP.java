package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client_UDP {

    public static void main(String[] args) throws Exception{

        InetAddress address = InetAddress.getByName("192.168.0.134");
        int port = 9999;  //服务器的端口号
        //创建发送方的数据报信息
        DatagramSocket socket = new DatagramSocket();  //创建套接字
        //////////////////////////////////////////////////////////////////////////////////////
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.execute(()->{
            try {
                sendMsg(socket,address,port);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        service.execute(()->{
            try {
                receiveMsg(socket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void sendMsg(DatagramSocket socket,InetAddress address, int port) throws Exception{
        while (true){
            Scanner sc = new Scanner(System.in);
            //System.out.println("请输入：");
            String str = sc.nextLine();  //读取字符串型输入
            System.out.println("我：【"+str+"】");
            if(str!=null){
                byte[] buf = str.getBytes();
                DatagramPacket dataGramPacket = new DatagramPacket(buf, buf.length, address, port);
                socket.send(dataGramPacket);  //通过套接字发送数据
            }
        }
    }

    public static void receiveMsg(DatagramSocket socket) throws Exception{
        while (true){
            //接收服务器反馈数据
            byte[] backbuf = new byte[1024];
            DatagramPacket backPacket = new DatagramPacket(backbuf, backbuf.length);
            socket.receive(backPacket);  //接收返回数据
            String backMsg = new String(backbuf, 0, backPacket.getLength());
            System.out.println("服务器:【" + backMsg+"】");
        }
    }
}
