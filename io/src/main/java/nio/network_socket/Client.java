package nio.network_socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {

    public static void main(String[] args) throws IOException {
        //打开socket连接，连接本地8090端口，也就是服务端
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8090));

        //请求服务端，发送请求
        ByteBuffer buf1 = ByteBuffer.allocate(1024);
        buf1.put("来着客户端的请求".getBytes());

        buf1.flip();
        if (buf1.hasRemaining())
            socketChannel.write(buf1);

        buf1.clear();

        //接受服务端的返回，构造接受缓冲区，我们定义头6个字节为头部，后续其他字节为主体内容。
        ByteBuffer header = ByteBuffer.allocate(6);
        ByteBuffer body   = ByteBuffer.allocate(1024);
        ByteBuffer[] bufferArray = { header, body };

        socketChannel.read(bufferArray);
        header.flip();
        body.flip();
        if (header.hasRemaining())
            System.out.println(">>>客户端接收头部数据：" + new String(header.array()));
        if (body.hasRemaining())
            System.out.println(">>>客户端接收body数据：" + new String(body.array()));
        header.clear();
        body.clear();

        socketChannel.close();
    }
}
