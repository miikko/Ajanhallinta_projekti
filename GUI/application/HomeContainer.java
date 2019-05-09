package application;

import controllers.GUI_Controller;
import controllers.LanguageUtil;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * This container implementation contains a Label with a greeting to the user
 * and a Stopwatch.<br>
 * 
 * @author miikk
 *
 */
class HomeContainer implements Container {
	private GUI_Controller controller;
	private VBox content;
	private Label welcomeLbl;

	public HomeContainer(GUI_Controller controller) {
		this.controller = controller;
		create();
	}

	/**
	 * Creates the content inside the container.<br>
	 * This method should only be called once, during object initialization.
	 */
	private void create() {
		content = new VBox();
		content.setAlignment(Pos.CENTER);
		welcomeLbl = new Label(LanguageUtil.translate("Welcome ") + controller.getUsername());
		content.getChildren().addAll(welcomeLbl, new Stopwatch(controller));
	}

	@Override
	public void refresh() {
		welcomeLbl.setText(LanguageUtil.translate("Welcome ") + controller.getUsername());
	}

	@Override
	public Node getContent() {
		return content;
	}

}
