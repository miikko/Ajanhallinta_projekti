package application;
	
import java.util.List;

import Models.Login;
import Models.Stopwatch;
import controllers.GUI_Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class View extends Application {
	
	private GUI_Controller controller;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();			
			Scene scene = new Scene(root,1000,700);
			controller = new GUI_Controller(this);
			VBox stopwatchContainer = controller.getStopwatch();
			List<Node> loginContainer = controller.getLoginContainer(5);
			VBox loginTextFields = (VBox) loginContainer.get(0);
			HBox loginBtns = (HBox) loginContainer.get(1);
			StackPane pieChart = controller.getPieChart();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene.getStylesheets().add(getClass().getResource("stopwatch.css").toExternalForm());
			root.setTop(pieChart);
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

