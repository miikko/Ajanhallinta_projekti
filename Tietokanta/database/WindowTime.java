package database;

/**
 * WindowTime contains the usage information for a specific application/browser-tab in a separate object.
 * One object is used per one application so information is stored in the same object
 * instead of creating multiple similar ones.
 * 
 * @author Arttuhal
 * @since 13/03/2019
 */

public class WindowTime {
	private String programName;
	private int  hrs = 0, mins = 0, secs = 0, millis = 0;
	
	/**
	 * A constructor that sets up the new WindowTime object.
	 * @param programName The name of the currently active program.
	 */
	public WindowTime(String programName) {
		this.programName = programName;
	}
	
	
}
