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
@SecondaryTable(name="Kayttaja_Data_Table", pkJoinColumns=@PrimaryKeyJoinColumn(name="userID", referencedColumnName="userID"))
public class Kayttaja {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int userID;
	
	@Column(name ="user_name")
	private String user_name;

	@Column(name="password")
	private String password;

	@Column(name="application", table="Kayttaja_Data_Table")
	private String application;
	
	@Column(name="time", table="Kayttaja_Data_Table")
	private String time;
	
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
	
	public String getApplication() {
		return application;
	}
	
	public void setApplication(String application) {
		this.application = application;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
}
