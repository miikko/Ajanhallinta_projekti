package service;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.net.MalformedURLException;

public class TaskBarNotification {
	
	//Displays a task bar notification with the given title and content
	public static void displayNotification(String title, String content) throws AWTException, MalformedURLException {
		SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
		TrayIcon trayIcon = new TrayIcon(image, "Temp");
		trayIcon.setImageAutoSize(true);
		trayIcon.setToolTip("Temp icon");
		tray.add(trayIcon);
		trayIcon.displayMessage(title, content, MessageType.INFO);
	}
}
