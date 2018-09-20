package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

public class ScatterGatherIO {

    public static void main(String[] args) throws IOException {
        String data = "Scattering and Gathering example shown in yiibai.com";
        gatherBytes(data);
        scatterBytes();
    }



    private static void gatherBytes(String data) throws IOException {
        // The First Buffer is used for holding a random number
        ByteBuffer buffer1 = ByteBuffer.allocate(8);

        // The Second Buffer is used for holding a data that we want to write
        ByteBuffer buffer2 = ByteBuffer.allocate(400);

        buffer1.asIntBuffer().put(420);
        buffer2.asCharBuffer().put(data);

        GatheringByteChannel gatherer = createChannelInstance("testout.txt", true);

        gatherer.write(new ByteBuffer[]{buffer1,buffer2});

    }




    private static void scatterBytes() throws IOException {
        // The First Buffer is used for holding a random number
        ByteBuffer buffer1 = ByteBuffer.allocate(8);
        // The Second Buffer is used for holding a data that we want to write
        ByteBuffer buffer2 = ByteBuffer.allocate(400);
        ScatteringByteChannel scatter = createChannelInstance("testout.txt", false);
        scatter.read(new ByteBuffer[] { buffer1, buffer2 });
        buffer1.rewind();
        buffer2.rewind();

    }

    private static FileChannel createChannelInstance(String file, boolean isOutput) {
        FileChannel FChannel = null;
        try {
            if (isOutput) {
                FChannel = new FileOutputStream(file).getChannel();
            } else {
                FChannel = new FileInputStream(file).getChannel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FChannel;
    }
}
