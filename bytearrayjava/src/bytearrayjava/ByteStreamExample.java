package bytearrayjava;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteStreamExample {
    public static void main(String[] args) throws IOException {
        
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream("C:\\Users\\BBORJA\\Downloads\\files\\source.txt");
            outStream = new FileOutputStream("C:\\Users\\BBORJA\\Downloads\\files\\dest.txt");
            
            // reads a byte at a time, if it reached end of the file, returns -1
            int content;
            while ((content = inStream.read()) != -1) {
                outStream.write((byte) content);
            }
        } finally {
            if (inStream != null) {
                inStream.close();
            }
            if (outStream != null) {
                outStream.close();
            }
        }
    }
}
