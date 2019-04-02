package database;

import java.util.List;

public interface KayttajaDAO_IF {
	public abstract boolean createKayttaja(Kayttaja kayttaja);
	public abstract Kayttaja readKayttaja(String user_name);
	public abstract Kayttaja[] readKayttajat();
	public abstract boolean updateKayttaja(Kayttaja kayttaja);
	public abstract boolean deleteKayttaja(String user_name);
	public abstract boolean updateSitting(int id);
	public abstract Kayttaja userExists(String username);
}
