package database;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;


/**
 * This class is all about the user data object
 * 
 * @author JP & miikk
 * @version 2.0
 * @since 08/03/2019
 */
@Entity
@Table(name="Kayttaja_Table")
public class Kayttaja {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int userId;
	
	@Column(name ="user_name")
	private String user_name;

	@Column(name="password")
	//@ColumnTransformer(forColumn="password", read="pgp_sym_decrypt(password), current_setting('encrypt.key')", write="pgp_sym_encrypt(?), current_setting('encrypt.key')")
	private String password;
	
	@Column
	@OneToMany(mappedBy="kayttaja")
	private Set<Sitting> sittings;
	
	public Kayttaja(String user_name, String password) {
		super();
		this.user_name = user_name;
		this.password = password;
	}
	
	// Tarvitaan käyttäjän updatemiseen
	public Kayttaja(int userId, String user_name, String password) {
		super();
		this.userId = userId;
		this.user_name = user_name;
		this.password = password;
	}
	
	public Kayttaja() {
		super();
	}
	public String getName() {
		return user_name;
	}
	
	public void setName(String user_name) {
		this.user_name = user_name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getId() {
		return userId;
	}
	
	public Set<Sitting> getSittings() {
		return sittings;
	}
	
	public void setSittings(Set<Sitting> sittings) {
		this.sittings = sittings;
	}
}
