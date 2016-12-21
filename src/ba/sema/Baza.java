package ba.sema;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.io.FilenameUtils;


public class Baza 
{
	/*
	public static void SnimiBase64String(String base64String)
	{
		Connection conn = null;
		try 
		{
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sema_test_db", "postgres", "postgres");
			String sql = "INSERT INTO captcha_slike (id, \"slikaBase64\", grupa_id) VALUES (?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 100);
			stmt.setString(2, base64String);
			stmt.setInt(3, 2);
			
			stmt.execute();
			stmt.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				conn.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	*/
	
	public static void SaveImageToDatabase(File file, int grupa) 
	{
		byte[] slikaByte = null;
		try 
		{
			slikaByte = Helper.ImageToByte(file);
		} 
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
		}
		
		Connection conn = null;
		try 
		{
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sema_test_db", "postgres", "postgres");
			PreparedStatement ps = conn.prepareStatement("INSERT INTO captcha_slike (nazivslike, ekstenzija, slika, grupa_id) VALUES (?, ?, ?, ?)");
			ps.setString(1, FilenameUtils.getBaseName(file.getName()));
			ps.setString(2, FilenameUtils.getExtension(file.getName()));
			ps.setBytes(3, slikaByte);
			ps.setInt(4, grupa);
			
			ps.executeUpdate();
			ps.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				conn.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public static SlikaModel GetImageModelFromDatabase(int id) 
	{
		SlikaModel model = new SlikaModel();
		Connection conn = null;
		try 
		{
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sema_test_db", "postgres", "postgres");
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM captcha_slike WHERE id = ?");
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{
				model.setId(rs.getInt(1));
				model.setNaziv(rs.getString(2));
				model.setEkstenzija(rs.getString(3));
				model.setSlikaByte(rs.getBytes(4));
				model.setGrupaId(rs.getInt(5));
			}
			
			rs.close();
			ps.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				conn.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return model;
	}
}
