package tests;

import javafx.scene.Scene;
import javafx.stage.Stage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import application.MainScreen;
import application.View;
import controllers.GUI_Controller;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 * 
 * Class is designed to include all the tests for the main screen. <br>
 * Tests different components and features of the screen.
 * 
 * @author MrJoXuX & JP
 *
 */

@ExtendWith(ApplicationExtension.class)
public class MainScreenTesting {
	private View view = new View();
	private MainScreen ms;
	private GUI_Controller gui = new GUI_Controller(view);
	private Scene scene;
	
	/**
	 * Tests the size of the main screen once it launches.
	 * @param stage
	 */
	
	@Start
    void onStart(Stage stage) {
        ms = new MainScreen(gui);
        scene = new Scene(ms, 1000, 700);
        stage.setScene(scene);
        stage.show();
        WaitForAsyncUtils.waitForFxEvents();
    }
	/*
	@Test
	void should_contain_menu_buttons() {
		verifyThat("#mainmenuBtn", hasText("Main menu"));
	}*/
	
    /*@Test
    void should_click_on_button(FxRobot robot) {
        // when:
        robot.clickOn("#optionMenu");

        // then:
        verifyThat("#accountItem", hasText("Account"));
        
        verifyThat("#logoutItem", hasText("Logout"));
        
        verifyThat("#quitItem", hasText("Quit"));
    }*/
}
