package Models;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class Login {
	private HBox tFContainer;
	private HBox btnContainer;
	private TextField usernameTF;
	private PasswordField passwordTF;
	private Button loginBtn;
	
	public Login() {
		
	}
	
	public HBox getLoginTextFields(int spacing) {
		tFContainer = new HBox(spacing);
		usernameTF = new TextField();
		usernameTF.setPromptText("Username");
		passwordTF = new PasswordField();
		passwordTF.setPromptText("Password");
		tFContainer.getChildren().addAll(usernameTF, passwordTF);
		return tFContainer;
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
				//Send info to DAO controller
			}
			
		});
		btnContainer.getChildren().addAll(loginBtn);
		return btnContainer;
	}
}
