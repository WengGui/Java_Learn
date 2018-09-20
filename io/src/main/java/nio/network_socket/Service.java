package nio.network_socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Service {

    public static void main(String[] args) throws IOException, InterruptedException {
        common_version();
    }

    // 普通版
    private static void common_version() throws IOException, InterruptedException {
        //创建一个服务 socket 并打开
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //监听绑定8090端口
        serverSocketChannel.socket().bind(new InetSocketAddress(8090));

        //设置为非阻塞模式
        serverSocketChannel.configureBlocking(false);

        while (true){
            //获取请求连接
            SocketChannel socketChannel = serverSocketChannel.accept();
            if( socketChannel!=null ){
                ByteBuffer buf1 = ByteBuffer.allocate(1024);
                socketChannel.read(buf1);   // 将客户端的信息读入 buff 中
                buf1.flip();
                if(buf1.hasRemaining()) // 打印客户端发来的信息
                    System.out.println(">>>服务端收到数据："+new String(buf1.array()));
                buf1.clear();

                //构造返回的报文，分为头部和主体，实际情况可以构造复杂的报文协议，这里只演示，不做特殊设计。
                ByteBuffer header = ByteBuffer.allocate(6);
                header.put("[head]".getBytes());
                ByteBuffer body   = ByteBuffer.allocate(1024);
                body.put("i am body!".getBytes());
                header.flip();
                body.flip();
                ByteBuffer[] bufferArray = { header, body };

                socketChannel.write(bufferArray);
                socketChannel.close();

            }else{
                Thread.sleep(1000);
            }
        }
    }

    // 选择器版
    private static void selector_version() throws IOException {
        //打开选择器
        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open(); //创建一个服务 socket 并打开
        serverSocketChannel.socket().bind(new InetSocketAddress(8090));  //监听绑定8090端口
        serverSocketChannel.configureBlocking(false);   //设置为非阻塞模式

        // 向通道注册选择器，并且注册接受事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // 获取已经准备好的通道数量
            int readyChannels = selector.selectNow();
            if (readyChannels == 0)  //如果没准备好，重试
                continue;

            //获取准备好的通道中的事件集合
            Set selectedKeys = selector.selectedKeys();
            Iterator keyIterator = selectedKeys.iterator();

            while(keyIterator.hasNext()){
                SelectionKey key = (SelectionKey)keyIterator.next();

                if (key.isAcceptable()) {
                    //在自己注册的事件中写业务逻辑，
                    //我这里注册的是accept事件，
                    //这部分逻辑和上面非选择器服务端代码一样。
                    ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverSocketChannel1.accept();
                    ByteBuffer buf1 = ByteBuffer.allocate(1024);
                    socketChannel.read(buf1);
                    buf1.flip();
                    if (buf1.hasRemaining())
                        System.out.println(">>>服务端收到数据：" + new String(buf1.array()));
                    buf1.clear();

                    ByteBuffer header = ByteBuffer.allocate(6);
                    header.put("[head]".getBytes());
                    ByteBuffer body = ByteBuffer.allocate(1024);
                    body.put("i am body!".getBytes());
                    header.flip();
                    body.flip();
                    ByteBuffer[] bufferArray = {header, body};
                    socketChannel.write(bufferArray);

                    socketChannel.close();
                }
            }
        }
    }
}
