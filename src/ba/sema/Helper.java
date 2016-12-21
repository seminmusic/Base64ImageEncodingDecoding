package ba.sema;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;


public class Helper 
{
	public static int[] FrameLocation(Dimension frameVelicina) 
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int)((screenSize.getWidth() - frameVelicina.getWidth()) / 2);
	    int y = (int)((screenSize.getHeight() - frameVelicina.getHeight()) / 2);
	    
	    return new int[] {x, y};
	}
	
	public static String Base64EncodeImage(byte[] imageByteArray) 
	{
        return Base64.encodeBase64String(imageByteArray);
    }
	
    public static byte[] Base64DecodeImage(String imageDataString) 
    {
    	return Base64.decodeBase64(imageDataString);
    }
    
    public static byte[] ImageToByte(File file) throws FileNotFoundException 
    {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try 
        {
            for (int readNum; (readNum = fis.read(buf)) != -1;) 
            {
                bos.write(buf, 0, readNum);      
                // System.out.println("Read " + readNum + " bytes");
            }
        } 
        catch (IOException ex) 
        {
        	
        }
        byte[] bytes = bos.toByteArray();
        System.out.println("Read " + bytes.length + " bytes from file");
        
        return bytes;
    }
}
