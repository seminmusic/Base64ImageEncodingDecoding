package ba.sema;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
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
			
			Date res_1 = new Date();
			ResponseModel response = Helper.GenerateResponseModel(brojRandomSlika, brojTacnihSlika);
			Date res_2 = new Date();
			System.out.println(response.toString());
			System.out.println("Generisanje response modela: " + Helper.RazlikaVremena(res_1, res_2));
			System.out.println("----------------------------------------------------------------------------\n");
		}
	}
}

// Test
