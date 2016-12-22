package ba.sema;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;


public class App 
{
	public App() 
	{
		super();
	}
	
	public static void main(String[] args) 
	{
		boolean startGUI = true;
		
		if (startGUI) 
		{
			Dimension frameVelicina = new Dimension(700, 500); 
			int[] frameLokacija = Helper.FrameLocation(frameVelicina);
			
			JFrame frame = new JFrame("Base64 Image Encoding and Decoding");
			frame.setSize(frameVelicina);
			frame.setLocation(frameLokacija[0], frameLokacija[1]);
			frame.setResizable(false);
			frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			
			MainPanel panel = new MainPanel();
			panel.setLayout(null);
			panel.postaviKomponente();
			
			frame.getContentPane().add(panel, "Center");
			frame.setVisible(true);
		}
		else
		{
			int brojRandomSlika = 6;
			int brojTacnihSlika = 3;
			
			Date db_1 = new Date();
				List<SlikaGrupaModel> listaRandomSlika = Baza.GetRandomImageModelsFromDatabase(brojRandomSlika);
			Date db_2 = new Date();
			System.out.println("\nUčitavanje liste od " + brojRandomSlika + " modela slika iz baze: " + Helper.RazlikaVremena(db_1, db_2));
			System.out.println("\nUčitani podaci:");
			for (SlikaGrupaModel model : listaRandomSlika) 
			{
				System.out.println(model.toString());
			}
			
			int[] idRandomSlika = listaRandomSlika.stream().map(s -> s.getId()).mapToInt(i -> i).toArray();
			System.out.println("\nID-ovi: " + Arrays.toString(idRandomSlika));
			int[] randomIndexiSlika = Helper.RandomIndexiNiza(idRandomSlika, brojTacnihSlika);
			System.out.println("Random " + brojTacnihSlika + " indexa: " + Arrays.toString(randomIndexiSlika));
			
			List<SlikaGrupaModel> listaTacnihSlika = new ArrayList<SlikaGrupaModel>();
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
			
			int[] idCorrectAnswers = listaTacnihSlika.stream().map(s -> s.getId()).mapToInt(i -> i).toArray();
			System.out.println("\nCorrect Answers: " + Arrays.toString(idCorrectAnswers));
			
			List<String> naziviGrupa = listaTacnihSlika.stream().map(s -> s.getGrupa_naziv()).collect(Collectors.toList());
			String securityQuestion = "Odaberite ";
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
		}
	}
}
