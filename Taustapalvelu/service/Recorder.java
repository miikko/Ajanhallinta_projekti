package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;

import database.Kayttaja;
import database.Sitting;
import database.SittingAccessObject;
import database.WindowTime;
import database.WindowTimeAccessObject;
import sun.awt.shell.ShellFolder;

/**
 * Class uses native library functions to record user activity outside of this application.<br>
 * Linking Java and native C libraries is done with the JNA library.
 * @author miikk
 * @since 12/3/2019
 */
public class Recorder extends Thread {
	private final int MAX_NAME_LENGTH = 1024;
	private final Kernel32 KERNEL32 = Kernel32.INSTANCE;
	private final User32 USER32 = User32.INSTANCE;
	private boolean quit;
	private Kayttaja user;
	private final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public Recorder(Kayttaja user) {
		this.user = user;
		this.setDaemon(true);
	}
	
	public void run() {
		Date startDate = new Date();
		Sitting sitting = new Sitting(user, DATE_FORMAT.format(startDate));
		SittingAccessObject sittingDAO = new SittingAccessObject();
		sittingDAO.createSitting(sitting);
		Set<WindowTime> windowTimes = new HashSet<>();
		WindowTimeAccessObject wtDAO = new WindowTimeAccessObject();
		WindowTime currWt = new WindowTime(sitting, getActiveProgramDescription());
		if (currWt.getProgramName() != null) {
			windowTimes.add(currWt);
			wtDAO.createWindowTime(currWt);
		}
		long timerNanoSecs = System.nanoTime();
		long wtStartTime = System.nanoTime();
		while (!quit) {
			//Add duration to current windowtime
			if (currWt.getProgramName() != null) {
				int secsPassed = (int) ((System.nanoTime() - wtStartTime) * Math.pow(10, -9));
				currWt.addTime(0, 0, secsPassed);
			}
			//Check if the active window is still the same
			String nextProgDescription = getActiveProgramDescription();
			if (!currWt.getProgramName().equals(nextProgDescription)) {
				//If it isn't save the windowtime to db and add it to the Set
				if (currWt.getProgramName() != null && !windowTimes.contains(currWt)) {
					wtDAO.createWindowTime(currWt);
					windowTimes.add(currWt);
				}
				//Then check if the new windowtime is found in the Set
				WindowTime nextWt = null;
				for (WindowTime wt : windowTimes) {
					if (wt.getProgramName().equals(nextProgDescription)) {
						nextWt = wt;
						break;
					}
				}
				//If it is, select the matching windowtime as the current windowtime
				if (nextWt != null) {
					currWt = nextWt;
				} else {
					//else create a new windowtime, set it as current and add it to the Set
					currWt = new WindowTime(sitting, nextProgDescription);
					if (nextProgDescription != null) {
						windowTimes.add(currWt);
					}
				}
				wtStartTime = System.nanoTime();
			} else if (currWt.getProgramName() != null){
				wtDAO.updateWindowTime(currWt);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//Send updated sitting to database every 60 seconds
			if (System.nanoTime() - timerNanoSecs >= 60 * Math.pow(10, 9)) {
				//Sets the endDate in case program execution stops before the thread is done running
				Date endDate = new Date();
				sitting.setEnd_date(DATE_FORMAT.format(endDate));
				sittingDAO.updateSitting(sitting);
				timerNanoSecs = System.nanoTime();
			}
		}
		Date endDate = new Date();
		sitting.setEnd_date(DATE_FORMAT.format(endDate));
		sittingDAO.updateSitting(sitting);
	}

	public void quit() {
		quit = true;
	}
	/**
	 * Uses User32 library's native methods to get the active window's name.
	 * @return active window's name (same as the text on the top left corner of the window).
	 */
	public String getActiveWindowName() {
		char[] buffer = new char[MAX_NAME_LENGTH];
		USER32.GetWindowText(USER32.GetForegroundWindow(), buffer, MAX_NAME_LENGTH);
		return Native.toString(buffer);
	}

	/**
	 * Uses native methods belonging to Kernel32 and User32 libraries to get the full path to executable file in control of the active window.<br>
	 * In Windows OS path starts from "This PC".
	 * @return full path to the active window's executable file.
	 */
	public String getActiveWindowFilePath() {
		HWND hWnd = USER32.GetForegroundWindow();
		IntByReference processId = new IntByReference();
		USER32.GetWindowThreadProcessId(hWnd, processId);
		HANDLE processHandle = KERNEL32.OpenProcess(Kernel32.PROCESS_QUERY_LIMITED_INFORMATION, false,
				processId.getValue());
		char[] buffer = new char[4096];
		IntByReference bufferSize = new IntByReference(buffer.length);
		boolean success = KERNEL32.QueryFullProcessImageName(processHandle, 0, buffer, bufferSize);
		KERNEL32.CloseHandle(processHandle);
		if (!success) {
			return null;
		}
		String fullPath = Native.toString(buffer);
		return fullPath;
	}

	/**
	 * Gets the active window's description using PowerShell commands.<br>
	 * The description in Windows OS is the same as in the Task-Manager's "Name"-column.<br>
	 * @return currently active program's description. If description is not found/is missing returns null.
	 */
	public String getActiveProgramDescription() {
		String description = null;
		String path = getActiveWindowFilePath();
		String exeName;
		try {
			exeName = path.split("\\\\")[path.split("\\\\").length - 1];
		} catch (NullPointerException e) {
			return "Description missing";
		}
		if (exeName != null) {
			// Remove the '.exe' ending from the name
			StringBuilder sb = new StringBuilder(exeName);
			sb.delete(exeName.length() - 4, exeName.length());
			String fileName = sb.toString();
			String command = "powershell.exe  get-process " + fileName + " | select-object description";
			BufferedReader br = null;
			try {
				Process powerShellProcess = Runtime.getRuntime().exec(command);
				powerShellProcess.getOutputStream().close();
				String line;
				int lineCounter = 0;
				br = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));
				while ((line = br.readLine()) != null) {
					lineCounter++;
					if (lineCounter == 4 && !line.equals("")) {
						description = line;
						break;
					}
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (description == null) {
			return "Description missing";
		}
		return description;
	}
	
	/**
	 * Uses ShellFolder to locate and store the active window's icon.
	 * @return Java Swing icon of the active window. Returns null if icon is not found/is missing.
	 */
	public Icon getActiveWindowIcon() {
		String path = getActiveWindowFilePath();
		File file = new File(path);
		Icon icon = null;
		try {
			ShellFolder shellFolder = ShellFolder.getShellFolder(file);
			icon = new ImageIcon(shellFolder.getIcon(true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return icon;
	}
}
