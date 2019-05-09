package application;

import controllers.GUI_Controller;
import javafx.scene.layout.Pane;

/**
 * A Factory class that creates Screen-objects.
 * 
 * @author miikk
 *
 */
public class ScreenFactory {

	/**
	 * Factory method for creating screens.
	 * 
	 * @param screenName Identifier for different screens.
	 * @param spacing    Global spacing to be used in the screen
	 * @param controller The GUI_Controller that gets passed to the created screen.
	 * @return a customized screen.
	 */
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
