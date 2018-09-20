package nio.timeServerDemo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class TimeQuery {

    private static int port = 8125;
    private static Charset charset = Charset.forName("US-ASCII");
    private static CharsetDecoder decoder = charset.newDecoder();

    // Direct byte buffer for reading
    private static ByteBuffer dbuf = ByteBuffer.allocateDirect(1024);

    private static void query(String host) throws IOException {

        InetSocketAddress isa = new InetSocketAddress(InetAddress.getByName(host),port);

        SocketChannel sc = SocketChannel.open();
        sc.connect(isa);

        // 从远程主机读取时间。为了简单起见，我们假设时间在一个数据包中返回，
        // 因此我们只需要读取一次。
        dbuf.clear();
        sc.read(dbuf);

        // Print the remote address and the received time
        dbuf.flip();
        CharBuffer cb = decoder.decode(dbuf);
        System.out.print(isa + " : " + cb);

        sc.close();
    }

    public static void main(String[] args) throws IOException {

        String host = InetAddress.getLocalHost().getHostAddress();
        System.out.println("Connect to Host:" + host + ", port:" + port);
        query(host);
    }
}
