package nio.readBigFile;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class ReadBigFile {

    public static void main(String[] args) throws FileNotFoundException {
        NIOReadBigFile();
    }

    /**
     * 使用NIO读取文件
     */
    private static void NIOReadBigFile() throws FileNotFoundException {
        URL url = ReadBigFile.class.getClassLoader().getResource("tokens.txt");
        String path = url.getPath();

        FileOutputStream  fos = new FileOutputStream(new File("D:/out.txt"));
        FileChannel outchannel = fos.getChannel();

        try {
            RandomAccessFile rdf = new RandomAccessFile(path, "rw");
            FileChannel inChannel=  rdf.getChannel();   //利用channel中的FileChannel来实现文件的读取
            ByteBuffer buf=  ByteBuffer.allocate(10);   //设置缓冲区容量为10

            //从通道中读取数据到缓冲区，返回读取的字节数量(把数据从磁盘中写入缓冲区)
            int byteRead=inChannel.read(buf);
            // 此时 buff 的 position为10   limit为10

            //数量为-1表示读取完毕。
            while (byteRead!=-1){
                //切换模式为读模式，其实就是把postion位置设置为0，可以从0开始读取
                buf.flip();

                outchannel.write(buf); // outChannel将缓存区的数据写到文件中后， position为10   limit为10
                buf.flip(); // 要重新将 postion 设置为0，才能读 buff 的内容
                while (buf.hasRemaining()) {//如果缓冲区还有数据
                    System.out.print((char) buf.get()); // 在控制台输出一个字符
                }

                buf.clear();                        //数据读完后清空缓冲区
                byteRead = inChannel.read(buf);     //继续把通道内剩余数据写入缓冲区
            }
            //关闭通道
            rdf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 普通IO，行读取
     */
    public static void readBigFile(){
        URL url = ReadBigFile.class.getClassLoader().getResource("tokens.txt");
        File file = new File(url.getFile());
        System.out.println(file.getAbsolutePath());
        BufferedReader reader=null;
        String temp=null;
        int line=1;
        try{
            reader=new BufferedReader(new FileReader(file));
            while((temp=reader.readLine())!=null){
                System.out.println("line"+line+":"+temp);
                line++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
