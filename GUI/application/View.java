package application;


import controllers.GUI_Controller;
import controllers.LanguageUtil;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Class is designed to show GUI content to user. <br>
 * View part in the MVC-framework
 * 
 * @author miikko & MrJoXuX & JP
 * @since 11/03/2019
 */
//TODO: Find a proper place to create screens
public class View extends Application {

	private GUI_Controller guiController;
	private Scene scene;
	private LoginScreen loginScreen;

	@Override
	public void start(Stage primaryStage) {
		try {
			guiController = new GUI_Controller(this);
			loginScreen = new LoginScreen(guiController, 5);
			scene = new Scene(loginScreen, 1000, 700);
			Image icon = new Image("file:assets/kello.png");
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//scene.getStylesheets().add(getClass().getResource("stopwatch.css").toExternalForm());
			primaryStage.setTitle("Ajanhallintapalvelu");
			primaryStage.getIcons().add(icon);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() {

	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Creates and shows a popup with the given message and a confirm button.<br>
	 * Clicking the button hides the popup and activates the given screen.
	 * 
	 * @param message
	 * @param transitionScreenName
	 */
	// TODO: Find a more elegant way for creating a popup
	public void createAndDisplayPopup(String message, String transitionScreenName) {
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		VBox popup = new VBox();
		Button confButton = new Button(LanguageUtil.translate("Confirm"));
		confButton.setId("confirmButton");
		confButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				popupStage.hide();
				guiController.activateScreen(transitionScreenName);
			}
		});
		popup.getChildren().addAll(new Label(message), confButton);
		popup.setAlignment(Pos.CENTER);
		popup.setPadding(new Insets(15));
		Scene popupScene = new Scene(popup);
		popupScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		popupStage.setScene(popupScene);
		popupStage.show();
	}

	public void setRoot(Pane screen) {
		scene.setRoot(screen);
	}

	public Scene getScene() {
		return scene;
	}
}
