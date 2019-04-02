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
	
	@Column(name ="start_time")
	private String start_time;
	
	@Column(name ="end_time")
	private String end_time;
	
	@ManyToOne
	@JoinColumn(name="userId", nullable=false)
	private Kayttaja kayttaja;
	
	//@OneToMany
	//private List<WindowTime> window_time;

	public Sitting() {
		super();
	}
	
	public Sitting(Kayttaja kayttaja) {
		super();
		this.kayttaja = kayttaja;
	}
	
	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	
	public int getId() {
		return sittingId;
	}

	public Kayttaja getKayttaja() {
		return kayttaja;
	}

}
