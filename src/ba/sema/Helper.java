package ba.sema;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;


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
    
    /*
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
        System.out.println("Read " + bytes.length + " bytes from file " + file.getName());
        
        return bytes;
    }
    */
    public static byte[] ImageToByte(File file)
    {
    	byte[] bytes = null;
		try 
		{
			bytes = FileUtils.readFileToByteArray(file);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
    	return bytes;
    }
    
    public static void SaveImageModelToFile(SlikaModel model, String folderZaSnimanje)
    {
    	String fileNaziv = "Iz baze - " + model.getNaziv() + "." + model.getEkstenzija();
    	String fullPutanja = FilenameUtils.concat(folderZaSnimanje, fileNaziv);
    	try 
    	{
			FileUtils.writeByteArrayToFile(new File(fullPutanja), model.getSlikaByte());
		} 
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    }
    
    public static String RazlikaVremena(Date start, Date kraj)
    {
    	long razlika = kraj.getTime() - start.getTime();
		long milisec = TimeUnit.MILLISECONDS.toMillis(razlika);
		long sec = TimeUnit.MILLISECONDS.toSeconds(razlika);
		
		return sec + " sekundi, " + milisec + " milisekundi"; 
    }
    
}
