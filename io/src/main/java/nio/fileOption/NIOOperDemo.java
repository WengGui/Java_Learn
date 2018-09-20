package nio.fileOption;

import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOOperDemo {

    private static  String  basePath = NIOOperDemo.class.getResource("/").getPath();
    private static String  filePath = NIOOperDemo.class.getClass().getResource("/tokens.txt").getPath();

    /**
     * 使用普通的 FileInputStream 读取文件
     * @throws IOException
     */
    public static void readFileByIO() throws IOException {

        FileInputStream fis = new FileInputStream(filePath);
        byte[] buffer=new byte[1024];
        int len=0;
        while((len=fis.read(buffer))!=-1){
            System.out.write(buffer,0,len);
        }
        fis.close();
    }

    /**
     * 使用普通的IO 按行读取文件
     * @throws IOException
     */
    public static void readFileByRow() throws IOException {

        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String temp;
        while((temp=reader.readLine())!=null){
            System.out.println(temp);
        }
    }
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    /**
     *
     * @throws IOException
     */
    public static void readByNIO() throws IOException {
        //第一步 获取通道
        FileInputStream fis = new FileInputStream(filePath);
        FileChannel channel = fis.getChannel();

        //文件内容的大小
        int size=(int) channel.size();

        //第二步 指定缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //第三步 将通道中的数据读取到缓冲区中
       // channel.read(buffer,);

        Buffer bf= buffer.flip();
        System.out.println("limt:"+bf.limit());

        byte[] bt=buffer.array();
        System.out.println(new String(bt,0,size));
    }


    public static void main(String[] args) throws IOException {
        //readFileByRow();
        readByNIO();
    }
}
