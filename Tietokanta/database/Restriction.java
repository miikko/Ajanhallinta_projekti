package database;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
		//super();
	}
	
	public Restriction(Kayttaja kayttaja, String weekday, int hours, int minutes) {
		//super();
		this.kayttaja = kayttaja;
		this.weekday = weekday;
		this.hours = hours;
		this.minutes = minutes;
	}
	
	public String getProgName() {
		return prog_name;
	}
}
