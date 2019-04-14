package application;

import controllers.GUI_Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Creates the login screen that is the first screen the user sees when logging
 * in.<br>
 * This screen contains 2 text fields for username and password and 2 buttons
 * for logging in and registering.<br>
 * Also adds the login screen to controllers screen pool so it can be chosen
 * later, for example when logging out.
 * 
 * @author miikk
 */
class LoginScreen extends BorderPane {
	private GUI_Controller controller;
	private VBox tfContainer;
	private HBox btnContainer;
	private TextField usernameTF;
	private PasswordField passwordTF;
	private Label inputInfoLbl;

	public LoginScreen(GUI_Controller controller, int spacing) {
		this.controller = controller;
		createLoginTextFields(spacing);
		createLoginBtnContainer(spacing);
		controller.addScreen("Login", this);
	}

	/**
	 * Initializes the login text fields and the pop up label and puts them in a
	 * container.
	 * 
	 * @param spacing
	 */
	private void createLoginTextFields(int spacing) {
		EventHandler<KeyEvent> inputHandler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (!inputInfoLbl.getText().equals("")) {
					inputInfoLbl.setText("");
				}
			}
		};
		tfContainer = new VBox();
		inputInfoLbl = new Label("");
		HBox subContainer = new HBox(spacing);
		usernameTF = new TextField();
		usernameTF.setOnKeyTyped(inputHandler);
		usernameTF.setPromptText("Username");
		passwordTF = new PasswordField();
		passwordTF.setPromptText("Password");
		passwordTF.setOnKeyTyped(inputHandler);
		subContainer.getChildren().addAll(usernameTF, passwordTF);
		subContainer.setStyle("-fx-border-color: black");
		subContainer.setAlignment(Pos.CENTER);
		tfContainer.getChildren().addAll(inputInfoLbl, subContainer);
		tfContainer.setAlignment(Pos.CENTER);
		this.setCenter(tfContainer);
	}

	/**
	 * Initializes the login buttons and puts them in a container.<br>
	 * 
	 * @param spacing
	 */
	private void createLoginBtnContainer(int spacing) {
		btnContainer = new HBox(spacing);
		btnContainer.setAlignment(Pos.CENTER);
		Button loginBtn = new Button("Login");
		loginBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String username = usernameTF.getText();
				String password = passwordTF.getText();
				if (!controller.handleLogin(username, password)) {
					inputInfoLbl.setText("Invalid username and/or password.");
				}
				passwordTF.clear();
			}
		});
		Button regBtn = new Button("Register");
		regBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String username = usernameTF.getText();
				String password = passwordTF.getText();
				if (!controller.handleRegister(username, password)) {
					inputInfoLbl.setText("Selected username is already taken. Please choose another one.");
				}
				passwordTF.clear();
			}

		});
		HBox.setMargin(loginBtn, new Insets(spacing));
		HBox.setMargin(regBtn, new Insets(spacing));
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.setStyle("-fx-border-color: black");
		btnContainer.getChildren().addAll(loginBtn, regBtn);
		this.setBottom(btnContainer);
	}
}
