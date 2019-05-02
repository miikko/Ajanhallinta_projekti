package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;

import sun.awt.shell.ShellFolder;

/**
 * Class contains static methods for getting information on active program windows.<br>
 * Most of the methods use Native functions from native C libraries. These methods work only on Windows OS.<br>
 * Linking Java and native C libraries is done with the JNA library.
 * @author miikk
 *
 */
public class WindowUtil {
	private static final int MAX_NAME_LENGTH = 1024;
	private static final Kernel32 KERNEL32 = Kernel32.INSTANCE;
	private static final User32 USER32 = User32.INSTANCE;
	
	/**
	 * Uses User32 library's native methods to get the active window's name.
	 * 
	 * @return active window's name (same as the text on the top left corner of the
	 *         window).
	 */
	public static String getActiveWindowName() {
		char[] buffer = new char[MAX_NAME_LENGTH];
		USER32.GetWindowText(USER32.GetForegroundWindow(), buffer, MAX_NAME_LENGTH);
		return Native.toString(buffer);
	}

	/**
	 * Uses native methods belonging to Kernel32 and User32 libraries to get the
	 * full path to executable file in control of the active window.<br>
	 * In Windows OS path starts from "This PC".
	 * 
	 * @return full path to the active window's executable file.
	 */
	public static String getActiveWindowFilePath() {
		int processId = getActiveWindowPID();
		HANDLE processHandle = KERNEL32.OpenProcess(Kernel32.PROCESS_QUERY_LIMITED_INFORMATION, false, processId);
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
	 * Gets the PID belonging to the currently active window.
	 * 
	 * @return the process id belonging to the active window
	 */
	private static int getActiveWindowPID() {
		HWND hWnd = USER32.GetForegroundWindow();
		IntByReference processId = new IntByReference();
		USER32.GetWindowThreadProcessId(hWnd, processId);
		return processId.getValue();
	}

	/**
	 * Gets the active window's description using PowerShell commands.<br>
	 * The description in Windows OS is the same as in the Task-Manager's
	 * "Name"-column.<br>
	 * 
	 * @return currently active program's description. If description is not
	 *         found/is missing returns null.
	 */
	public static String getActiveProgramDescription() {
		String description = null;
		String path = getActiveWindowFilePath();
		String exeName;
		Process powerShellProcess = null;
		try {
			exeName = path.split("\\\\")[path.split("\\\\").length - 1];
		} catch (Exception e) {
			return null;
		}
		if (exeName != null) {
			// Remove the '.exe' ending from the name
			StringBuilder sb = new StringBuilder(exeName);
			sb.delete(exeName.length() - 4, exeName.length());
			String fileName = sb.toString();
			String command = "powershell.exe  get-process " + fileName + " | select-object description";
			BufferedReader br = null;
			try {
				powerShellProcess = Runtime.getRuntime().exec(command);
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
				powerShellProcess.destroy();
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
				if (powerShellProcess != null && powerShellProcess.isAlive()) {
					powerShellProcess.destroyForcibly();
				}
			}
		}
		if (description == null || description.trim().length() == 0) {
			return null;
		}
		return description.trim();
	}
	
	/**
	 * Destroys the process and all the child processes belonging to the window.
	 * @return true if the window was closed successfully, false otherwise.
	 */
	public static boolean closeActiveWindow() {
		String exeName;
		String filePath = getActiveWindowFilePath();
		try {
			exeName = filePath.split("\\\\")[filePath.split("\\\\").length - 1];
		} catch(Exception e) {
			return false;
		}
		if (isProcessRunning(exeName)) {
			try {
				Process killProc = Runtime.getRuntime().exec("taskkill /f /t /im " + exeName);
				killProc.waitFor();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}
	
	private static boolean isProcessRunning(String exeName) {
		Process listProc = null;
		BufferedReader br = null;
		try {
			listProc = Runtime.getRuntime().exec("tasklist");
			br = new BufferedReader(new InputStreamReader(listProc.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains(exeName)) {
					return true;
				}
			}
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
			if (listProc != null && listProc.isAlive()) {
				listProc.destroyForcibly();
			}
		}
		return false;
	}

	/**
	 * Uses ShellFolder to locate and store the active window's icon.
	 * 
	 * @return Java Swing icon of the active window. Returns null if icon is not
	 *         found/is missing.
	 */
	public static Icon getActiveWindowIcon() {
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
