package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;

public class Service_UDP {
    public static void main(String[] args) throws Exception {

        InetAddress address = InetAddress.getLocalHost();
        int port = 9999;
        //创建DatagramSocket对象
        DatagramSocket socket = new DatagramSocket(port, address);

        while (true){
            byte[] buf = new byte[1024];  //定义byte数组
            DatagramPacket packet = new DatagramPacket(buf, buf.length);  //创建DatagramPacket对象
            socket.receive(packet);  //通过套接字接收数据
            String getMsg = new String(buf, 0, packet.getLength());
            System.out.println("客户端发送的数据为：【" + getMsg+"】");

            //从服务器返回给客户端数据
            InetAddress clientAddress = packet.getAddress(); //获得客户端的IP地址
            int clientPort = packet.getPort(); //获得客户端的端口号
            SocketAddress sendAddress = packet.getSocketAddress();
            String feedback = "行啦，我收到了！！";
            byte[] backbuf = feedback.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(backbuf, backbuf.length, sendAddress); //封装返回给客户端的数据
            socket.send(sendPacket);  //通过套接字反馈服务器数据
        }
    }
}
