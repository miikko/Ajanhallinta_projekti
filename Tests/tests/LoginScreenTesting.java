package tests;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import application.LoginScreen;
import application.View;
import controllers.GUI_Controller;
import database.DatabaseHandler;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 * 
 * Class is designed to include all the tests for the login screen. <br>
 * Tests different components and features of the screen.
 * 
 * @author MrJoXuX & JP
 *
 */

@ExtendWith(ApplicationExtension.class)
public class LoginScreenTesting{
	private View view = new View();
	private GUI_Controller gui = new GUI_Controller(view);
	private LoginScreen login;
	private Scene scene;
	private static DatabaseHandler dh = new DatabaseHandler();
	
	@Start
	public void onStart(Stage stage) throws Exception {
		login = new LoginScreen(gui,5);
		scene = new Scene(login, 1000, 700);
		stage.setScene(scene);
		stage.show();
		WaitForAsyncUtils.waitForFxEvents();
	}
	
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		dh.deleteUser("TestiUser");
	}
	
	/**
	 * Tests whether the login screen has <br>
	 * the login button.
	 */
	
	@Test
	void hasLoginButton() {
		verifyThat("#LoginButton", hasText("Login"));
	}
	
	/**
	 * Tests whether the login screen has the application's name
	 */
	@Test
	void hasAppNameText() {
		verifyThat(".label", hasText("Time Manager"));
	}
	
	/**
	 * Tests whether the login screen has <br>
	 * the register button.
	 */
	@Test
	void hasRegisterButton() {
		verifyThat("#RegisterButton", hasText("Register"));
	}
	
	/**
	 * Tests user login with empty text fields.
	 * @param robot
	 */
	@Test
	void testEmptyLogin(FxRobot robot) {
		robot.clickOn("#userText").write("");
		robot.clickOn("#passText").write("");
		robot.clickOn("#LoginButton");
		verifyThat("#infoLabel", hasText("Invalid username and/or password."));
	}
	
	/**
	 * Tests that user cannot login with wrong password
	 * @param robot
	 */
	@Test
	void testWrongPassword(FxRobot robot) {
		robot.clickOn("#userText").write("temp");
		robot.clickOn("#passText").write("asdfsdagsg");
		robot.clickOn("#LoginButton");
		verifyThat("#infoLabel", hasText("Invalid username and/or password."));
	}
	
	/**
	 * Tests user registration with empty text fields.
	 * @param robot
	 */
	@Test
	void testEmptyRegister(FxRobot robot) {
		robot.clickOn("#userText").write("");
		robot.clickOn("#passText").write("");
		robot.clickOn("#RegisterButton");
		verifyThat("#infoLabel", hasText("Selected username is already taken. Please choose another one."));
	}
	
	/**
	 * Tests successful user registration.
	 * @param robot
	 */
	@Test
	void testSuccessfulRegistration(FxRobot robot) {
		robot.clickOn("#userText").write("TestiUser");
		robot.clickOn("#passText").write("TestiSalasana");
		robot.clickOn("#RegisterButton");
		verifyThat("#infoLabel", hasText("Registration succesful!"));
		robot.clickOn("#confirmButton"); // Ei osaa vielä mennä confirm nappun jälkeen eteenpäin
	}
	
	/**
	 * Tests duplicate username registration.
	 * @param robot
	 */
	@Test
	void testDuplicateUsernameLogin(FxRobot robot) {
		robot.clickOn("#userText").write("TestiUser");
		robot.clickOn("#passText").write("aasdsadasdasd");
		robot.clickOn("#RegisterButton");
		verifyThat("#infoLabel", hasText("Selected username is already taken. Please choose another one."));
	}
	
	
}
