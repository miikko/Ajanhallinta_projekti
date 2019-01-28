package service;

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
	
	//Returns the active window's full name
	public String getActiveWindowName() {
		char[] buffer = new char[MAX_NAME_LENGTH];
		user32.GetWindowText(user32.GetForegroundWindow(), buffer, MAX_NAME_LENGTH);
		return Native.toString(buffer);
	}
	
	//Returns the .exe file name belonging to the active window
	public String getActiveWindowExeName() {
		HWND hWnd = user32.GetForegroundWindow();
		IntByReference processId = new IntByReference();
		user32.GetWindowThreadProcessId(hWnd, processId);
		HANDLE processHandle = kernel32.OpenProcess(Kernel32.PROCESS_QUERY_LIMITED_INFORMATION, false, processId.getValue());
		char[] buffer = new char[4096];
		IntByReference bufferSize = new IntByReference(buffer.length);
		boolean success = kernel32.QueryFullProcessImageName(processHandle, 0, buffer, bufferSize);
		kernel32.CloseHandle(processHandle);
		if (!success) {
			return null;
		}
		String fullPath = Native.toString(buffer);
		return fullPath.split("\\\\")[fullPath.split("\\\\").length - 1];
	}
}
