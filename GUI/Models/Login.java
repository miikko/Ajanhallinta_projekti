package Models;

import database.Kayttaja;
import database.KayttajaAccessObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Login {
	//Kommentoi tämä, jos ei ole lokaalia tietokantaa
	//static KayttajaAccessObject kayttajaDAO = new KayttajaAccessObject();
	private VBox tfContainer;
	private HBox subContainer;
	private HBox btnContainer;
	private TextField usernameTF;
	private PasswordField passwordTF;
	private Button loginBtn;
	private Button regBtn;
	private Label popUp;
	
	public Login() {
		
	}
	
	public VBox getLoginTextFields(int spacing) {
		EventHandler<KeyEvent> inputHandler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (!popUp.getText().equals("")) {
					popUp.setText("");
				}
			}
		};
		tfContainer = new VBox();
		popUp = new Label("");
		subContainer = new HBox(spacing);
		usernameTF = new TextField();
		usernameTF.setOnKeyTyped(inputHandler);
		usernameTF.setPromptText("Username");
		passwordTF = new PasswordField();
		passwordTF.setPromptText("Password");
		passwordTF.setOnKeyTyped(inputHandler);
		subContainer.getChildren().addAll(usernameTF, passwordTF);
		subContainer.setStyle("-fx-border-color: black");
		subContainer.setAlignment(Pos.CENTER);
		tfContainer.getChildren().addAll(popUp, subContainer);
		tfContainer.setAlignment(Pos.CENTER);
		return tfContainer;
	}
	
	public HBox getBtnContainer(int spacing) {
		btnContainer = new HBox(spacing);
		btnContainer.setAlignment(Pos.CENTER);
		loginBtn = new Button("Login");
		loginBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String username = usernameTF.getText();
				String password = passwordTF.getText();
				System.out.println("Username: " + username + ", password: " + password);
				//TODO: Check if username and password exist in database
				boolean userExists = false;
				if (!userExists) {
					popUp.setText("Invalid username and/or password.");
				}
				//Kommentoi nämä, jos ei ole lokaalia tietokantaa
				//Kayttaja kayttaja = new Kayttaja(username, password);
				//kayttajaDAO.createKayttaja(kayttaja);
			}
		});
		regBtn = new Button("Register");
		regBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String username = usernameTF.getText();
				String password = passwordTF.getText();
				//TODO: Check if username exists
				boolean userNameTaken = true;
				if (userNameTaken) {
					popUp.setText("Selected username is already taken. Please choose another one.");
				}
				//TODO: Create a new user
				
			}
			
		});
		HBox.setMargin(loginBtn, new Insets(spacing));
		HBox.setMargin(regBtn, new Insets(spacing));
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.setStyle("-fx-border-color: black");
		btnContainer.getChildren().addAll(loginBtn, regBtn);
		return btnContainer;
	}
}
