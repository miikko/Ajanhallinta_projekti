package application;

import controllers.GUI_Controller;
import javafx.scene.layout.Pane;

public class ScreenFactory {
	
	public static Pane createScreen(String screenName, int spacing, GUI_Controller controller) {
		switch (screenName) {
		case "Login":
			return new LoginScreen(controller, spacing);
		case "Main":
			return new MainScreen(controller);
		default:
			return null;
		}
	}
}
