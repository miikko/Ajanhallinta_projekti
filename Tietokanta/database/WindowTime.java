package database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * WindowTime contains the usage information for a specific
 * application/browser-tab in a separate object. One object is used per one
 * application so information is stored in the same object instead of creating
 * multiple similar ones.
 * 
 * @author Arttuhal & miikk
 * @since 13/03/2019
 */

@Entity
@Table(name = "WindowTime_Table")

public class WindowTime {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int wtId;

	@Column(name = "programName")
	private String programName;

	@Column(name = "duration")
	private String duration;

	@ManyToOne
	@JoinColumn(name = "sittingId", nullable = false)
	private Sitting sitting;

	@Transient
	private int hours = 0;

	@Transient
	private int minutes = 0;

	@Transient
	private int seconds = 0;

	public WindowTime() {
		super();
	}

	/**
	 * A constructor that sets up the new WindowTime object.
	 * 
	 * @param programName The name of the currently active program.
	 */
	public WindowTime(String programName) {
		super();
		this.programName = programName;
	}

	public WindowTime(Sitting sitting, String programName) {
		super();
		this.sitting = sitting;
		this.programName = programName;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Sitting getSitting() {
		return sitting;
	}

	public void setSitting(Sitting sitting) {
		this.sitting = sitting;
	}

	/**
	 * Adds the given time and modifies the duration-variable accordingly
	 * 
	 * @param h
	 * @param min
	 * @param sec
	 */
	public void addTime(int h, int min, int sec) {
		seconds += sec;
		while (seconds >= 60) {
			seconds -= 60;
			minutes++;
		}
		minutes += min;
		while (minutes >= 60) {
			minutes -= 60;
			hours++;
		}
		hours += h;
		duration = hours + ":" + minutes + ":" + seconds;
	}

	public int getHours() {
		return hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public int getSeconds() {
		return seconds;
	}

}
