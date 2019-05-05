package database;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;;

@Entity
@Table(name="Restriction_Table")
public class Restriction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int restrictionId;
	
	@Column(name="prog_name")
	private String prog_name;
	
	@Column(name="weekday")
	private String weekday;
	
	@Column(name="hours")
	private int hours;
	
	@Column(name="minutes")
	private int minutes;
	
	@ManyToOne
	@JoinColumn(name="userId", nullable=false)
	private Kayttaja kayttaja;
	
	public Restriction() {
		super();
	}
	
	public Restriction(Kayttaja kayttaja, String progName, String weekday, int hours, int minutes) {
		super();
		this.kayttaja = kayttaja;
		this.prog_name = progName;
		this.weekday = weekday;
		this.hours = hours;
		this.minutes = minutes;
	}
	
	public String getProgramName() {
		return prog_name;
	}
	
	public String getWeekday() {
		return weekday;
	}
	
	public int getHours() {
		return hours;
	}
	
	public void setHours(int hours) {
		this.hours = hours;
	}
	
	public int getMinutes() {
		return minutes;
	}
	
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
}
