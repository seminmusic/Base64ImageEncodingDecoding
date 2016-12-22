package ba.sema;

public class SlikaGrupaModel 
{
	private int id;
	private String nazivSlike;
	private String ekstenzija;
	private byte[] slika;
	private int grupa_id;
	private String grupa_naziv;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNazivSlike() {
		return nazivSlike;
	}
	public void setNazivSlike(String nazivSlike) {
		this.nazivSlike = nazivSlike;
	}
	public String getEkstenzija() {
		return ekstenzija;
	}
	public void setEkstenzija(String ekstenzija) {
		this.ekstenzija = ekstenzija;
	}
	public byte[] getSlika() {
		return slika;
	}
	public void setSlika(byte[] slika) {
		this.slika = slika;
	}
	public int getGrupa_id() {
		return grupa_id;
	}
	public void setGrupa_id(int grupa_id) {
		this.grupa_id = grupa_id;
	}
	public String getGrupa_naziv() {
		return grupa_naziv;
	}
	public void setGrupa_naziv(String grupa_naziv) {
		this.grupa_naziv = grupa_naziv;
	}
	
	@Override 
	public String toString()
	{
		return String.format("ID: %3d,  Naziv slike: %-15s,  Ekstenzija: %-5s,  Slika byte length: %8d,  Grupa ID: %2d,  Naziv grupe: %-15s", 
							id, nazivSlike, ekstenzija, slika.length, grupa_id, grupa_naziv);
	}
}
