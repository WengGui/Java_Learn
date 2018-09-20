package nio.timeServerDemo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Date;

/* 监听网络连接，并告诉客户端时间
 * Demonstrates NIO socket channels (accepting and writing),
 * buffer handling, charsets, and regular expressions.
 */
public class TimeServer {

    private static int port = 8125;
    private static Charset charset = Charset.forName("US-ASCII");
    private static CharsetEncoder encoder = charset.newEncoder();

    // Direct byte buffer for writing
    private static ByteBuffer dbuf = ByteBuffer.allocateDirect(1024);


    /**
     * 开启并绑定 server-socket 通道
     * @return
     * @throws IOException
     */
    private static ServerSocketChannel setup() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        String host = InetAddress.getLocalHost().getHostAddress();
        System.out.println("Listen at Host:" + host + ", port:" + port);
        InetSocketAddress isa = new InetSocketAddress(host,port);
        ssc.socket().bind(isa);
        return ssc;
    }

    // 在给定的通道中为下一个请求服务
    // Service the next request to come in on the given channel
    private static void serve(ServerSocketChannel ssc) throws IOException {
        SocketChannel sc = ssc.accept();
        String now = new Date().toString();
        sc.write(encoder.encode(CharBuffer.wrap(now+"\r\n")));
        sc.close();
    }


    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = setup();
        while (true){
            serve(ssc);
        }
    }
}
