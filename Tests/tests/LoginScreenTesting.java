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

import application.LoginScreen;
import application.View;
import controllers.GUI_Controller;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
public class LoginScreenTesting{

	private View view = new View();
	private LoginScreen login;
	private GUI_Controller gui = new GUI_Controller(view);
	private Scene scene;
	
	@Start
	public void onStart(Stage stage) throws Exception {
		login = new LoginScreen(gui,5);
		scene = new Scene(login, 1000, 700);
		stage.setScene(scene);
		stage.show();
	}
	
	@Test
	public void hasLoginButton() {
		verifyThat("#LoginButton", hasText("Login"));
	}
	
	@Test
	public void hasRegisterButton() {
		verifyThat("#RegisterButton", hasText("Register"));
	}
	
	/*
	@Test
	public void should_click_button(FxRobot robot) {
		robot.clickOn(".button");
		verifyThat(".button", hasText("Logged in"));
	}
	*/
}
