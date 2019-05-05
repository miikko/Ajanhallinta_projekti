package service;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

/**
 * This Thread-class is responsible of displaying OS notifications to users<br>
 * The notification disappears automatically after a set time.
 * @author miikk
 * @since 12/3/2019
 */
public class TaskBarNotification extends Thread {
	private String title;
	private String content;
	private final int DURATION = 5000;
	
	public TaskBarNotification(String title, String content) {
		this.title = title;
		this.content = content;
		this.setDaemon(true);
	}
	
	@Override
	public void run() {
		try {
			SystemTray tray = SystemTray.getSystemTray();
			Image image = Toolkit.getDefaultToolkit().createImage("assets/kello.png");
			TrayIcon trayIcon = new TrayIcon(image, "Temp");
			trayIcon.setImageAutoSize(true);
			trayIcon.setToolTip("Temp icon");
			tray.add(trayIcon);
			trayIcon.displayMessage(title, content, MessageType.INFO);
			Thread.sleep(DURATION);
			tray.remove(trayIcon);
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
