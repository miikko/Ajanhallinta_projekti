package tests;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.TextInputControlMatchers;

import application.LoginScreen;
import application.Stopwatch;
import application.View;
import controllers.GUI_Controller;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
public class StopwatchTesting{
	
	private View view = new View();
	private Stopwatch sw;
	private GUI_Controller gui = new GUI_Controller(view);
	private Scene scene;
	
	 @Start
	 void onStart(Stage stage) {
		 sw = new Stopwatch(gui);
		 scene = new Scene(sw, 1000, 700);
	     stage.setScene(scene);
	     stage.show();
	 }

	 @Test
	 void should_contain_button(FxRobot robot) {
	     // expect:
	     verifyThat(".button", hasText("Start recording"));
	     robot.clickOn(".button");
	     
	 }

	 @Test
	 void should_contain_timer() {
		 verifyThat("#TimerNum",  TextInputControlMatchers.hasText("00:00:00"));
		 
	 }
	 
	/* @Test
	 void should_click_on_button(FxRobot robot) {
	     // when:
	     robot.clickOn(".button");

	     // then:
	     verifyThat(".button", hasText("clicked!"));
	 }*/
}
