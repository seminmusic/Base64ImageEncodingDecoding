package ba.sema;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    
    public static int[] RandomIndexiNiza(int[] niz, int broj)
    {
    	return new Random().ints(0, niz.length).distinct().limit(broj).toArray();
    }
    
    public static List<SlikaGrupaModel> ListaTacnihSlika(List<SlikaGrupaModel> listaRandomSlika, int brojTacnihSlika)
    {
    	List<SlikaGrupaModel> listaTacnihSlika = new ArrayList<SlikaGrupaModel>();
    	//
    	int[] idRandomSlika = listaRandomSlika.stream().map(s -> s.getId()).mapToInt(i -> i).toArray();
    	System.out.println("\nID-ovi: " + Arrays.toString(idRandomSlika));
		int[] randomIndexiSlika = Helper.RandomIndexiNiza(idRandomSlika, brojTacnihSlika);
		System.out.println("Random " + brojTacnihSlika + " indexa: " + Arrays.toString(randomIndexiSlika));
		for (int index : randomIndexiSlika)
		{
			int id = idRandomSlika[index];
			SlikaGrupaModel tacan = listaRandomSlika.stream().filter(s -> s.getId() == id).findFirst().get();
			listaTacnihSlika.add(tacan);
		}
		System.out.println("\nTačni podaci:");
		for (SlikaGrupaModel model : listaTacnihSlika)
		{
			System.out.println(model.toString());
		}
    	//
    	return listaTacnihSlika;
    }
    
    public static String SecurityQuestion(List<SlikaGrupaModel> listaTacnihSlika)
    {
    	String securityQuestion = "Odaberite ";
    	//
    	List<String> naziviGrupa = listaTacnihSlika.stream().map(s -> s.getGrupa_naziv()).collect(Collectors.toList());
		if (naziviGrupa.size() > 1) 
		{
			String naziviGrupaJoin = String.join(", ", naziviGrupa);
			String naziviGrupaFormatirani = new StringBuilder(naziviGrupaJoin).replace(naziviGrupaJoin.lastIndexOf(","), naziviGrupaJoin.lastIndexOf(",") + 1, " i").toString();
			securityQuestion += naziviGrupaFormatirani + ".";
		}
		else if (naziviGrupa.size() == 1)
		{
			securityQuestion += naziviGrupa.get(0) + ".";
		}
		System.out.println("\nSecurity Question: " + securityQuestion);
		//
		return securityQuestion;
    }
    
    public static ResponseModel GenerateResponseModel(int brojRandomSlika, int brojTacnihSlika)
    {
    	Date db_1 = new Date();
		List<SlikaGrupaModel> listaRandomSlika = Baza.GetRandomImageModelsFromDatabase(brojRandomSlika);
		Date db_2 = new Date();
		System.out.println("\nUčitavanje liste od " + brojRandomSlika + " modela slika iz baze: " + Helper.RazlikaVremena(db_1, db_2));
		System.out.println("\nUčitani podaci:");
		for (SlikaGrupaModel model : listaRandomSlika) 
		{
			System.out.println(model.toString());
		}
		
		List<SlikaGrupaModel> listaTacnihSlika = Helper.ListaTacnihSlika(listaRandomSlika, brojTacnihSlika);
		
		int[] idCorrectAnswers = listaTacnihSlika.stream().map(s -> s.getId()).mapToInt(i -> i).toArray();
		System.out.println("\nCorrect Answers: " + Arrays.toString(idCorrectAnswers));
		
		String securityQuestion = Helper.SecurityQuestion(listaTacnihSlika);
		
		ResponseModel response = new ResponseModel();
		response.setSecurityQuestion(securityQuestion);
		response.setListaSlika(Helper.ListaBase64EnkodiranihSlika(listaRandomSlika));
		response.setCorrectAnswers(idCorrectAnswers);
		//
		return response;
    }
    
    public static List<ResponseSlika> ListaBase64EnkodiranihSlika(List<SlikaGrupaModel> inLista)
    {
    	List<ResponseSlika> outLista = new ArrayList<ResponseSlika>();
    	//
    	for (SlikaGrupaModel in : inLista)
    	{
    		ResponseSlika out = new ResponseSlika();
    		out.setId(in.getId());
    		out.setBase64Slika(Helper.Base64EncodeImage(in.getSlika()));
    		outLista.add(out);
    	}
    	//
    	return outLista;
    }
    
}
