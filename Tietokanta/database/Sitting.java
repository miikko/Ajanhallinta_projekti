package database;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

/**
 * This class includes the tables used in sittings.
 * 
 * @author Arttuhal & miikk
 * @since 13/03/2019
 */

@Entity
@Table(name="Sitting_Table")
public class Sitting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int sittingId;
	
	@Column(name ="start_date")
	private String start_date;
	
	@Column(name ="end_date")
	private String end_date;
	
	@ManyToOne
	@JoinColumn(name="userId", nullable=false)
	private Kayttaja kayttaja;
	
	//@OneToMany
	//private List<WindowTime> window_time;

	public Sitting() {
		super();
	}
	
	public Sitting(Kayttaja kayttaja, String start_date) {
		super();
		this.kayttaja = kayttaja;
		this.start_date = start_date;
	}
	
	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	
	public int getId() {
		return sittingId;
	}

	public Kayttaja getKayttaja() {
		return kayttaja;
	}

}
