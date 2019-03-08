package database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

@Entity
@Table(name="Kayttaja_Table")
public class Kayttaja {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int userID;
	
	@Column(name ="user_name")
	private String user_name;

	@Column(name="password")
	private String password;
	
	public Kayttaja(String user_name, String password) {
		super();
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
		return userID;
	}
}
