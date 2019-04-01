package database;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * WindowTime contains the usage information for a specific
 * application/browser-tab in a separate object. One object is used per one
 * application so information is stored in the same object instead of creating
 * multiple similar ones.
 * 
 * @author Arttuhal
 * @since 13/03/2019
 */

@Entity
@Table(name = "WindowTime_Table")

public class WindowTime {
	private String programName;
	private int hrs = 0, mins = 0, secs = 0;

	/**
	 * A constructor that sets up the new WindowTime object.
	 * 
	 * @param programName The name of the currently active program.
	 */
	public WindowTime(String programName) {
		this.programName = programName;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public int getHrs() {
		return hrs;
	}

	public int getMins() {
		return mins;
	}

	public int getSecs() {
		return secs;
	}

	public void setTime(int hrs, int mins, int secs) {
		this.hrs = hrs;
		this.mins = mins;
		this.secs = secs;
	}

}
