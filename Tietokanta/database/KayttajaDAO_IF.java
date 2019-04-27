package database;

public interface KayttajaDAO_IF {
	public abstract boolean createKayttaja(Kayttaja kayttaja);

	public abstract Kayttaja readKayttaja(String user_name);
	
	public Kayttaja readKayttaja(int userId);

	public abstract Kayttaja[] readKayttajat();

	public abstract boolean updateKayttaja(Kayttaja kayttaja);

	public abstract boolean deleteKayttaja(String user_name);

}
