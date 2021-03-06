package ba.sema;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FilenameUtils;


@SuppressWarnings("serial")
public class MainPanel extends JPanel
{
	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
	
	JButton btnIzaberiSliku;
	JLabel lblGrupaId;
	JTextField txtGrupaId;
	JFileChooser slikaChooser;
	JLabel lblSlikaPreview;
	JTextArea txtBase64Slika;
	JLabel lblSlikaId;
	JTextField txtSlikaId;
	JButton btnDownloadSlike;
	JLabel lblDownloadStatus;
	
	public void postaviKomponente() 
	{
		btnIzaberiSliku = new JButton(" Izaberi slike ...", UIManager.getIcon("FileView.fileIcon"));
		btnIzaberiSliku.setBounds(30, 30, 150, 30);
		btnIzaberiSliku.setFocusPainted(false);
		btnIzaberiSliku.addActionListener(btnIzaberiSlikuListener());
		//
		lblGrupaId = new JLabel("ID grupe u bazi:");
		lblGrupaId.setBounds(200, 30, 100, 30);
		//
		txtGrupaId = new JTextField();
		txtGrupaId.setBounds(295, 30, 70, 31);
		//
		lblSlikaPreview = new JLabel("");
		lblSlikaPreview.setBounds(30, 90, 200, 150);
		lblSlikaPreview.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		//
		txtBase64Slika = new JTextArea("");
		txtBase64Slika.setBounds(260, 90, 410, 150);
		txtBase64Slika.setEditable(false);
		txtBase64Slika.setLineWrap(true);
		//
		lblSlikaId = new JLabel("ID slike u bazi:");
		lblSlikaId.setBounds(30, 270, 150, 30);
		//
		txtSlikaId = new JTextField();
		txtSlikaId.setBounds(120, 270, 70, 31);
		//
		btnDownloadSlike = new JButton(" Download slike iz baze", UIManager.getIcon("FileView.fileIcon"));
		btnDownloadSlike.setBounds(210, 270, 200, 30);
		btnDownloadSlike.setFocusPainted(false);
		btnDownloadSlike.addActionListener(btnDownloadSlikeListener());
		//
		lblDownloadStatus = new JLabel("");  // "<html><div>Test</div><div>Opet</div></html>"
		lblDownloadStatus.setBounds(30, 320, 640, 100);
		lblDownloadStatus.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		lblDownloadStatus.setVerticalAlignment(JLabel.TOP);
		
		add(btnIzaberiSliku);
		add(lblGrupaId);
		add(txtGrupaId);
		add(lblSlikaPreview);
		add(txtBase64Slika);
		add(lblSlikaId);
		add(txtSlikaId);
		add(btnDownloadSlike);
		add(lblDownloadStatus);
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
				slikaChooser.setDialogTitle("Izaberi slike");
				slikaChooser.setMultiSelectionEnabled(true);
				slikaChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				slikaChooser.setAcceptAllFileFilterUsed(false);  // Disable the "All files" option
				slikaChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg"));
				//
				if (slikaChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
				{
					txtBase64Slika.setText("");
					
					File[] slikeFiles = slikaChooser.getSelectedFiles();
					
					String grupaIdString = txtGrupaId.getText().trim();
					if (!grupaIdString.equalsIgnoreCase("")) 
					{
						int grupaId = Integer.parseInt(grupaIdString);
					
						/*
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
						*/
					
						Date snimanje_1 = new Date();
						System.out.println("Start snimanja: " + dateFormat.format(snimanje_1));
						//
						for (File slikaFile : slikeFiles)
						{
							Baza.SaveImageToDatabase(slikaFile, grupaId);
						}
						//
						Date snimanje_2 = new Date();
						System.out.println("Završetak snimanja: " + dateFormat.format(snimanje_2));
						System.out.println("Trajanje snimanja: " + Helper.RazlikaVremena(snimanje_1, snimanje_2));
						
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
				} 
				else 
				{
					//System.out.println("No Selection");
				}
			}
		};
	}
	
	private ActionListener btnDownloadSlikeListener() 
	{
		return new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				String slikaIdString = txtSlikaId.getText().trim();
				if (!slikaIdString.equalsIgnoreCase("")) 
				{
					Date download_1 = new Date();
						SlikaModel model = Baza.GetImageModelFromDatabase(Integer.parseInt(slikaIdString));
					Date download_2 = new Date();
					String download = "Download podataka slike iz baze: " + Helper.RazlikaVremena(download_1, download_2);
					
					String folderZaSnimanje = FilenameUtils.concat(System.getProperty("user.dir"), "slike_download_iz_baze");
					Date save_1 = new Date();
						Helper.SaveImageModelToFile(model, folderZaSnimanje);
					Date save_2 = new Date();
					String save = "Snimanje slike u fajl: " + Helper.RazlikaVremena(save_1, save_2);
					
					lblDownloadStatus.setText("<html>" + "<div>" + download + "</div>" + "<div>" + save + "</div>" + "</html>");
				}
			}
		};
	}
	
}
