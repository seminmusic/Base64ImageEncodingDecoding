package ba.sema;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;


@SuppressWarnings("serial")
public class MainPanel extends JPanel
{
	JButton btnIzaberiSliku;
	JFileChooser slikaChooser;
	JLabel lblSlikaPreview;
	JTextArea txtBase64Slika;
	
	public void postaviKomponente() 
	{
		btnIzaberiSliku = new JButton(" Izaberi sliku ...", UIManager.getIcon("FileView.fileIcon"));
		btnIzaberiSliku.setBounds(30, 30, 150, 30);
		btnIzaberiSliku.setFocusPainted(false);
		btnIzaberiSliku.addActionListener(btnIzaberiSlikuListener());
		//
		lblSlikaPreview = new JLabel("");
		lblSlikaPreview.setBounds(30, 90, 200, 150);
		lblSlikaPreview.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		//
		txtBase64Slika = new JTextArea("");
		txtBase64Slika.setBounds(260, 90, 410, 150);
		txtBase64Slika.setEditable(false);
		txtBase64Slika.setLineWrap(true);
		
		add(btnIzaberiSliku);
		add(lblSlikaPreview);
		add(txtBase64Slika);
	}
	
	private ActionListener btnIzaberiSlikuListener() 
	{
		return new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				UIManager.put("FileChooser.readOnly", Boolean.TRUE);  // Disable rename/new in chooser
				slikaChooser = new JFileChooser();
				slikaChooser.setCurrentDirectory(new java.io.File("."));
				slikaChooser.setDialogTitle("Izaberi sliku");
				slikaChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				slikaChooser.setAcceptAllFileFilterUsed(false);  // Disable the "All files" option
				slikaChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg"));
				//
				if (slikaChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
				{
					txtBase64Slika.setText("");
					
					File slikaFile = slikaChooser.getSelectedFile();
					try 
					{
						Image slikaPreview = ImageIO.read(slikaFile).getScaledInstance(200, 150, BufferedImage.SCALE_SMOOTH);
						ImageIcon iconPreview = new ImageIcon(slikaPreview);
				        lblSlikaPreview.setIcon(iconPreview);
				        lblSlikaPreview.setVisible(true);
					} 
					catch (IOException e1) 
					{
						e1.printStackTrace();
					}
					
					DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
					Date startSnimanja = new Date();
					System.out.println("Start snimanja: " + dateFormat.format(startSnimanja));
					//
					Baza.SaveImageToDatabase(slikaFile, 3);
					//
					Date zavrsetakSnimanja = new Date();
					System.out.println("Završetak snimanja: " + dateFormat.format(zavrsetakSnimanja));
					//
					long razlika = zavrsetakSnimanja.getTime() - startSnimanja.getTime();
					long miliSec = TimeUnit.MILLISECONDS.toMillis(razlika);
					long sec = TimeUnit.MILLISECONDS.toSeconds(razlika);
					System.out.println("Trajanje snimanja: " + miliSec + " milisekundi, " + sec + " sekundi");
					
					
					/*
					FileInputStream slikaStream = null;
					String slikaBase64String = null;
					try 
					{
						// Konverzija slike u bajte:
						slikaStream = new FileInputStream(slikaFile);
						byte slikaByte[] = new byte[(int) slikaFile.length()];
			            slikaStream.read(slikaByte);
			            
			            // Konverzija bajta slike u Base64 string:
			            slikaBase64String = Helper.Base64EncodeImage(slikaByte);
					} 
					catch (FileNotFoundException e1) 
					{
						e1.printStackTrace();
					} 
					catch (IOException e2) 
					{
						e2.printStackTrace();
					}
					finally 
					{
						try 
						{
							slikaStream.close();
						} 
						catch (IOException e1) 
						{
							e1.printStackTrace();
						}
					}
					
					txtBase64Slika.setText(slikaBase64String);
					Baza.SnimiBase64String(slikaBase64String);
					*/
				} 
				else 
				{
					//System.out.println("No Selection");
				}
			}
		};
	}
}
