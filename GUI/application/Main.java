package application;
	
import Models.Stopwatch;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();			
			Scene scene = new Scene(root,600,300);
			Stopwatch stopwatch = new Stopwatch();
			VBox vBox = stopwatch.getVBox();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene.getStylesheets().add(getClass().getResource("stopwatch.css").toExternalForm());
			root.setTop(vBox);
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
