package database;

import java.util.HashSet;


/**This Thread-class is responsible for sending updates concerning the current sitting to the database. 
 * @author miikk
 *
 */
public class SittingManager extends Thread{
	
	private enum TaskType {
		CREATE_SITTING,
		UPDATE_SITTING,
		CREATE_WINDOWTIME,
		UPDATE_WINDOWTIME,
		NONE,
	}
	
	private KayttajaAccessObject userObject;
	private SittingAccessObject sittingDAO;
	private Kayttaja user;
	private Sitting sitting;
	private WindowTime wt;
	private boolean exit;
	private TaskType taskType;
	private HashSet<String> progNames;
	
	public SittingManager (Kayttaja user) {
		this.user = user;
		userObject = new KayttajaAccessObject();
		sittingDAO = new SittingAccessObject();
		progNames = new HashSet<String>();
		this.setDaemon(true);
		taskType = TaskType.NONE;
	}
	
	@Override
	public void run() {
		while (!exit) {
			switch (taskType) {
				
			}
		}
	}
	
	public void quit() {
		exit = true;
	}
	
	public Sitting createSitting(String startDate) {
		sitting = new Sitting(user, startDate);
		taskType = TaskType.CREATE_SITTING;
		return sitting;
	}
	
	public Sitting updateSitting() {
	
		return null;
	}
	
	public WindowTime createWindowTime(String progName) {
		wt = new WindowTime(sitting, progName);
		taskType = TaskType.CREATE_WINDOWTIME;
		return wt;
	}
	
	public void updateWindowTime(WindowTime wt) {
		
	}
}
