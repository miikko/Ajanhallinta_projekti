package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;

public class Recorder {
	private final int MAX_NAME_LENGTH = 1024;
	private final Kernel32 kernel32 = Kernel32.INSTANCE;
	private final User32 user32 = User32.INSTANCE;

	public Recorder() {

	}

	// Returns the active window's full name
	public String getActiveWindowName() {
		char[] buffer = new char[MAX_NAME_LENGTH];
		user32.GetWindowText(user32.GetForegroundWindow(), buffer, MAX_NAME_LENGTH);
		return Native.toString(buffer);
	}

	// Returns the .exe file belonging to the active window
	public String getActiveWindowFileName() {
		HWND hWnd = user32.GetForegroundWindow();
		IntByReference processId = new IntByReference();
		user32.GetWindowThreadProcessId(hWnd, processId);
		HANDLE processHandle = kernel32.OpenProcess(Kernel32.PROCESS_QUERY_LIMITED_INFORMATION, false,
				processId.getValue());
		char[] buffer = new char[4096];
		IntByReference bufferSize = new IntByReference(buffer.length);
		boolean success = kernel32.QueryFullProcessImageName(processHandle, 0, buffer, bufferSize);
		kernel32.CloseHandle(processHandle);
		if (!success) {
			return null;
		}
		String fullPath = Native.toString(buffer);
		// return fullPath;
		//Delete path leading to the .exe file
		return fullPath.split("\\\\")[fullPath.split("\\\\").length - 1];
	}

	//Returns the currently active program's description
	//Program description can be found by right-clicking the file in File-explorer and selecting properties
	//Or alternatively it can be found in the Task-Manager's "Name"-column
	public String getActiveProgramDescription() {
		String description = null;
		String exeName = getActiveWindowFileName();
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
		return description;
	}
}
