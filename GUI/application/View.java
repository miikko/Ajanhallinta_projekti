package application;
	
import Models.Login;
import Models.Stopwatch;
import controllers.GUI_Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class View extends Application {
	
	private GUI_Controller controller;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();			
			Scene scene = new Scene(root,600,300);
			controller = new GUI_Controller(this);
			VBox stopwatchContainer = controller.getStopwatch();
			HBox[] loginContainer = controller.getLoginContainer(5);
			HBox loginTextFields = loginContainer[0];
			HBox loginBtns = loginContainer[1];
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene.getStylesheets().add(getClass().getResource("stopwatch.css").toExternalForm());
			root.setTop(stopwatchContainer);
			root.setCenter(loginTextFields);
			root.setBottom(loginBtns);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void init() {
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

