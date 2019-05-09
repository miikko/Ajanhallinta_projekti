package database;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



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
	private String password;
	
	@Column
	@OneToMany(mappedBy="kayttaja")
	private Set<Sitting> sittings;
	
	@Column
	@OneToMany(mappedBy="kayttaja")
	private List<Restriction> restrictions;
	
	public Kayttaja(String user_name, String password) {
		super();
		this.user_name = user_name;
		this.password = password;
	}
	
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
