package service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import controllers.DateUtil;
import database.DatabaseHandler;
import database.Kayttaja;
import database.Sitting;
import database.WindowTime;

/**
 * This Thread class records user activity outside of this application.<br> 
 * The recorded data is periodically sent to the database.<br>
 * 
 * @author miikk
 * @since 12/3/2019
 */
public class Recorder extends Thread {
	private boolean quit;
	private Kayttaja user;
	private Set<WindowTime> windowTimes = new HashSet<>();
	private DatabaseHandler dbHandler;
	private WindowTime currWt = null;

	public Recorder(Kayttaja user, DatabaseHandler dbHandler) {
		this.user = user;
		this.dbHandler = dbHandler;
		this.setDaemon(true);
	}

	@Override
	public void run() {
		LocalDateTime startDate = LocalDateTime.now();
		Sitting sitting = new Sitting(user, DateUtil.dateToString(startDate));
		dbHandler.sendSitting(sitting);
		String currProgDescription = WindowUtil.getActiveProgramDescription();
		if (currProgDescription != null) {
			currWt = new WindowTime(sitting, currProgDescription);
			windowTimes.add(currWt);
			dbHandler.sendWindowTime(currWt);
		}
		long timerNanoSecs = System.nanoTime();
		long wtIntervalTime = System.nanoTime();
		while (!quit) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String nextProgDescription = WindowUtil.getActiveProgramDescription();
			// Check if active window is still the same
			if ((currWt == null && nextProgDescription == null)
					|| (currWt != null && currWt.getProgramName().equals(nextProgDescription))) {
				if (currWt != null) {
					int secsPassed = (int) ((System.nanoTime() - wtIntervalTime) * Math.pow(10, -9));
					currWt.addTime(0, 0, secsPassed);
					dbHandler.updateWindowTime(currWt);
				}
			} else {
				handleActiveWindowChange(nextProgDescription, sitting);
			}
			wtIntervalTime = System.nanoTime();
			// Send updated sitting to database every 60 seconds
			// Set the endDate in case application execution stops before the thread has
			// finished running
			if (System.nanoTime() - timerNanoSecs >= 60 * Math.pow(10, 9)) {
				LocalDateTime endDate = LocalDateTime.now();
				sitting.setEnd_date(DateUtil.dateToString(endDate));
				dbHandler.updateSitting(sitting);
				timerNanoSecs = System.nanoTime();
			}
			/*
			String temp = WindowUtil.getActiveProgramDescription();
			System.out.println(temp);
			if (temp != null && temp.equals("Discord")) {
				System.out.println("foo");
				WindowUtil.closeActiveWindow();
			}*/
		}
		LocalDateTime endDate = LocalDateTime.now();
		sitting.setEnd_date(DateUtil.dateToString(endDate));
		dbHandler.updateSitting(sitting);
	}

	public void quit() {
		quit = true;
	}

	/**
	 * This method should be called each time the user switches active windows.<br>
	 * It changes the currWt-variable's value depending on the given parameters.<br>
	 * Additionally it sends a new WindowTime object to the database if the current
	 * active window has not been active in current Sitting.
	 * 
	 * @param nextProgDescription
	 * @param sitting
	 */
	private void handleActiveWindowChange(String nextProgDescription, Sitting sitting) {
		currWt = null;
		if (nextProgDescription != null) {
			for (WindowTime wt : windowTimes) {
				if (nextProgDescription.equals(wt.getProgramName())) {
					currWt = wt;
					break;
				}
			}
			if (currWt == null) {
				currWt = new WindowTime(sitting, nextProgDescription);
				windowTimes.add(currWt);
				dbHandler.sendWindowTime(currWt);
			}
		}
	}
	
}
