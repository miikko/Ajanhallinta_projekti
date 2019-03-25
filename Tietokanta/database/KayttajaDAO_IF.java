package database;

import java.util.List;

public interface KayttajaDAO_IF {
	public abstract boolean createKayttaja(Kayttaja kayttaja);
	public abstract Kayttaja readKayttaja(int id);
	public abstract Kayttaja[] readKayttajat();
	public abstract boolean updateKayttaja(Kayttaja kayttaja);
	public abstract boolean deleteKayttaja(int id);
	public abstract boolean updateSitting(int id);
	public abstract Kayttaja[] userExists(String username);
}
