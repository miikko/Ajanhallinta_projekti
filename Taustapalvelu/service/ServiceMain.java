package service;

import java.awt.AWTException;
import java.net.MalformedURLException;

public class ServiceMain {

	public static void main(String[] args) {
		try {
			TaskBarNotification.displayNotification();
		} catch (MalformedURLException | AWTException e) {
			e.printStackTrace();
		}
	}

}
