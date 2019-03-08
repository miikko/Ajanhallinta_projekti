package database;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class KayttajaAccessObject implements KayttajaDAO_IF{
	SessionFactory istuntotehdas = Istuntotehdas.annaIstuntotehdas();
	Transaction transaktio = null;
	
	@Override
	public boolean createKayttaja(Kayttaja kayttaja) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			istunto.saveOrUpdate(kayttaja);
			transaktio.commit();
			return true;
		}catch(Exception e) {
			if(transaktio != null) 
				transaktio.rollback();
			throw e;
			
		}finally {
			istunto.close();
		}
	}

	@Override
	public Kayttaja readKayttaja(int id) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			Kayttaja kayttaja = new Kayttaja();
			istunto.load(kayttaja, id);
			transaktio.commit();
			return kayttaja;
		}catch(Exception e) {
			if(transaktio != null)
				transaktio.rollback();
			throw e;
		}finally {
			istunto.close();
		}
		
	}

	@Override
	public Kayttaja[] readKayttajat() {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Kayttaja> kayttajat = istunto.createQuery("from Kayttaja").getResultList();
			transaktio.commit();
			Kayttaja[] returnArray = new Kayttaja[kayttajat.size()];
			return (Kayttaja[]) kayttajat.toArray(returnArray);
		}catch (Exception e) {
			if(transaktio != null)
				transaktio.rollback();
			throw e;
		}finally {
			istunto.close();
		}
	}

	@Override
	public boolean updateKayttaja(Kayttaja kayttaja) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			istunto.saveOrUpdate(kayttaja);
			
			transaktio.commit();
			return true;
		}catch (Exception e) {
			if(transaktio != null)
				transaktio.rollback();
			throw e;
		}finally {
			istunto.close();
		}
	}

	@Override
	public boolean deleteKayttaja(int id) {
		Session istunto = istuntotehdas.openSession();
		try {
			Kayttaja kayt = readKayttaja(id);
			transaktio = istunto.beginTransaction();
			istunto.delete(kayt);
			transaktio.commit();
			return true;
		}catch (Exception e) {
			if(transaktio != null)
				transaktio.rollback();
			throw e;
		}finally {
			istunto.close();
		}
	}
	
	public boolean updateSitting(int id) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			istunto.saveOrUpdate(id);
			
			transaktio.commit();
			return true;
		}catch (Exception e) {
			if(transaktio != null)
				transaktio.rollback();
			throw e;
		}finally {
			istunto.close();
		}
	}
}
