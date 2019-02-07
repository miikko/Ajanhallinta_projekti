package database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Kayttaja_Table")
public class Kayttaja {

	@Id
	@Column(name ="user_name")
	private String user_name;
	
	@Column(name="application")
	private String application;
	
	@Column(name="password")
	private String password;
	
	@Column(name="time")
	private String time;
	
	public Kayttaja(String user_name, String application, String time, String password) {
		super();
		this.user_name = user_name;
		this.application = application;
		this.time = time;
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
	
	public String getApp() {
		return application;
	}
	
	public void setApp(String application) {
		this.application = application;
	}

	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
