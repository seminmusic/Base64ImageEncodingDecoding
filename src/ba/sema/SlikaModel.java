package ba.sema;

public class SlikaModel 
{
	private int id;
	private String naziv;
	private String ekstenzija;
	private byte[] slikaByte;
	private int grupaId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getEkstenzija() {
		return ekstenzija;
	}
	public void setEkstenzija(String ekstenzija) {
		this.ekstenzija = ekstenzija;
	}
	public byte[] getSlikaByte() {
		return slikaByte;
	}
	public void setSlikaByte(byte[] slikaByte) {
		this.slikaByte = slikaByte;
	}
	public int getGrupaId() {
		return grupaId;
	}
	public void setGrupaId(int grupaId) {
		this.grupaId = grupaId;
	}
	
	@Override 
	public String toString() 
	{
	    StringBuilder result = new StringBuilder();
	    
	    result.append("<div>ID: " + id + "</div>");
	    result.append("<div>Naziv slike: " + naziv + ", Ekstenzija: " + ekstenzija + "</div>");
	    result.append("<div>Byte length: " + slikaByte.length + "</div>");
	    result.append("<div>Grupa ID: " + grupaId + "</div>");
	    
	    return result.toString();
	}
}
