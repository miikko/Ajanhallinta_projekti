package service;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.net.MalformedURLException;

/**
 * Class contains methods for displaying OS notifications to users
 * @author miikk
 * @since 12/3/2019
 */
public class TaskBarNotification {
	
	public TaskBarNotification() {
		
	}
	
	/**
	 * Displays a task bar notification with the given title and content.
	 * @param title
	 * @param content
	 * @throws AWTException
	 * @throws MalformedURLException
	 */
	public static void displayNotification(String title, String content) throws AWTException, MalformedURLException {
		SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
		TrayIcon trayIcon = new TrayIcon(image, "Temp");
		trayIcon.setImageAutoSize(true);
		trayIcon.setToolTip("Temp icon");
		tray.add(trayIcon);
		trayIcon.displayMessage(title, content, MessageType.INFO);
	}
	
	public int simpleSum() {
		return 1 + 1;
	}
}
