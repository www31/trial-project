package bytearrayjava;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteArrayStreamDemo {
	public static void main(String[] args) throws Throwable {
	    fileOutput();
	    byteArrMethod(args);
	    sample1();
	    sample2();
	}
	
	private static void fileOutput() throws IOException {
	    String message = "This is a test of byte array stream";
        byte[] source = message.getBytes();
        
        ByteArrayInputStream bais = new ByteArrayInputStream(source);
        int c;
        while((c = bais.read()) != -1) {
            //System.out.print((char) c);
        }
        bais.reset();

        byte[] b = new byte[100];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(source);
        System.out.println("this is written by outputstream.."+baos.toString());
        
        FileOutputStream fos = new FileOutputStream("C:\\Users\\BBORJA\\Downloads\\files\\output.txt");
        baos.writeTo(fos);
	}
	
	private static void byteArrMethod(String[] arg) throws Throwable {
        File f = new File(arg[0]);
        InputStream in = new FileInputStream(f);

        byte[] buff = new byte[8000];

        int bytesRead = 0;

        ByteArrayOutputStream bao = new ByteArrayOutputStream();

        while((bytesRead = in.read(buff)) != -1) {
           bao.write(buff, 0, bytesRead);
        }

        byte[] data = bao.toByteArray();

        ByteArrayInputStream bin = new ByteArrayInputStream(data);
        System.out.println(bin.available());
	}
	
	private static void sample1() {
        // Create byteArrayOutputStream 
        ByteArrayOutputStream byteArrayOutStr 
            = new ByteArrayOutputStream(); 
  
        // Write byte 
        // to byteArrayOutputStream 
  
        byteArrayOutStr.write(71); 
  
        byteArrayOutStr.write(69); 
  
        byteArrayOutStr.write(69); 
  
        byteArrayOutStr.write(75); 
  
        byteArrayOutStr.write(83); 
  
        // Print the byteArrayOutputStream 
        System.out.println( 
            byteArrayOutStr.toString()); 
	}
	
	private static void sample2() {
        // Create byteArrayOutputStream 
        ByteArrayOutputStream byteArrayOutStr 
            = new ByteArrayOutputStream(); 
  
        // Create byte array 
        byte[] buf = { 71, 69, 69, 75, 83, 
                       70, 79, 82, 71, 69, 
                       69, 75, 83 }; 
  
        // Write byte array 
        // to byteArrayOutputStream 
        byteArrayOutStr.write(buf, 8, 5); 
  
        // Print the byteArrayOutputStream 
        System.out.println( 
            byteArrayOutStr.toString());
	}
}
