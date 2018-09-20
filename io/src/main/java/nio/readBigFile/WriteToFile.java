package nio.readBigFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class WriteToFile {

    public static void main(String[] args) {

    }


    public static void write() throws FileNotFoundException {
        String filename = "D:/out.txt";
        FileOutputStream fos = null;

        fos = new FileOutputStream(new File(filename));
        FileChannel channel = fos.getChannel();

    }
}
