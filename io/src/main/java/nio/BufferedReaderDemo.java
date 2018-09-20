package nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BufferedReaderDemo {

    public static void main(String[] args) throws IOException {
        Path file = null;
        BufferedReader bufferedReader = null;

        file = Paths.get("testOut.txt");
        InputStream inputStream = Files.newInputStream(file);

        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        System.out.println(bufferedReader.readLine());
    }
}
