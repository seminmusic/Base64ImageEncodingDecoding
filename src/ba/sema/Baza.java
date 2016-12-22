package ba.sema;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;


public class Baza 
{
	private final static String db_url  = "jdbc:postgresql://localhost:5432/sema_test_db";
	private final static String db_user = "postgres";
	private final static String db_pass = "postgres";
	
	/*
	public static void SnimiBase64String(String base64String)
	{
		Connection conn = null;
		try 
		{
			conn = DriverManager.getConnection(db_url, db_user, db_pass);
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
		byte[] slikaByte = Helper.ImageToByte(file);
		
		Connection conn = null;
		try 
		{
			conn = DriverManager.getConnection(db_url, db_user, db_pass);
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
			conn = DriverManager.getConnection(db_url, db_user, db_pass);
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
	
	public static List<SlikaGrupaModel> GetRandomImageModelsFromDatabase(int brojRandomSlika)
	{
		List<SlikaGrupaModel> lista = new ArrayList<SlikaGrupaModel>();
		Connection conn = null;
		try 
		{
			conn = DriverManager.getConnection(db_url, db_user, db_pass);
			String upit = "SELECT SLIKA.*, GRUPA.naziv grupa_naziv FROM " +
						  "( " +
						  "    SELECT DISTINCT ON (grupa_id) id, nazivslike, ekstenzija, slika, grupa_id " + 
						  "    FROM captcha_slike " + 
						  "    ORDER BY grupa_id, random() " +
						  ") SLIKA " +
						  "LEFT JOIN captcha_grupe GRUPA ON GRUPA.id = SLIKA.grupa_id " +
						  "ORDER BY random() " +
						  "LIMIT ?";
			PreparedStatement ps = conn.prepareStatement(upit);
			ps.setInt(1, brojRandomSlika);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) 
			{
				SlikaGrupaModel model = new SlikaGrupaModel();
				model.setId(rs.getInt("id"));
				model.setNazivSlike(rs.getString("nazivslike"));
				model.setEkstenzija(rs.getString("ekstenzija"));
				model.setSlika(rs.getBytes("slika"));
				model.setGrupa_id(rs.getInt("grupa_id"));
				model.setGrupa_naziv(rs.getString("grupa_naziv"));
				lista.add(model);
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
		return lista;
	}
	
}
