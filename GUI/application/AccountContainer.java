package application;

import controllers.GUI_Controller;
import controllers.LanguageUtil;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;

/**
 * This container contains TextFields for changing the username and
 * password.<br>
 * Also contains Labels for guidance and for checking out the userId.
 * 
 * @author miikk & MrJoXuX & JP
 *
 */
class AccountContainer implements Container {
	private GUI_Controller controller;
	private VBox content;
	private PasswordField passwordField;
	private PasswordField passwordField2;
	private Label infoLbl;
	private Label idLbl;
	private TextField usernameField;
	private final String STYLE = "-fx-background-color: rgba(167, 197, 204, 0.5);";

	public AccountContainer(GUI_Controller controller) {
		this.controller = controller;
		create();
	}

	private void create() {
		idLbl = new Label(LanguageUtil.translate("Your Id: ") + controller.getUserId());
		infoLbl = new Label("");
		content = new VBox();
		content.setSpacing(20);
		content.setAlignment(Pos.CENTER);
		content.getChildren().addAll(idLbl, createUsernameContent(), createPasswordContent(), infoLbl);
	}

	private VBox createUsernameContent() {
		Label usern = new Label(LanguageUtil.translate("New username"));
		usernameField = new TextField();
		usernameField.setId("changeUN");
		Button changeUnameBtn = new Button(LanguageUtil.translate("Change"));
		changeUnameBtn.setOnAction((ActionEvent event) -> {
			String username = usernameField.getText();
			if (controller.handleUsernameChange(username)) {
				infoLbl.setText(LanguageUtil.translate("Username was changed successfully!"));
				usernameField.clear();
			} else {
				infoLbl.setText(LanguageUtil.translate("Failed to change username."));
			}
		});
		VBox usernameContent = new VBox();
		usernameContent.setBackground(Background.EMPTY);
		usernameContent.setPrefSize(100, 150);
		usernameContent.setAlignment(Pos.CENTER);
		usernameContent.setStyle(STYLE);
		usernameContent.getChildren().addAll(usern, usernameField, changeUnameBtn);
		return usernameContent;
	}

	private VBox createPasswordContent() {
		Label pass1 = new Label(LanguageUtil.translate("New password"));
		Label pass2 = new Label(LanguageUtil.translate("Type same password"));
		passwordField = new PasswordField();
		passwordField.setId("changePW1");
		passwordField2 = new PasswordField();
		passwordField.setId("changePW2");
		Button changePwBtn = new Button(LanguageUtil.translate("Change"));
		changePwBtn.setOnAction((ActionEvent event) -> {
			String setPass1 = passwordField.getText();
			String setPass2 = passwordField2.getText();
			if (controller.handlePasswordChange(setPass1, setPass2)) {
				infoLbl.setText(LanguageUtil.translate("Password was changed successfully!"));
			} else {
				infoLbl.setText(LanguageUtil.translate("Failed to change password."));
			}
			passwordField.clear();
			passwordField2.clear();
		});
		VBox passwordContent = new VBox();
		passwordContent.setBackground(Background.EMPTY);
		passwordContent.setPrefSize(100, 250);
		passwordContent.setSpacing(5);
		passwordContent.setStyle(STYLE);
		passwordContent.setAlignment(Pos.CENTER);
		passwordContent.setPadding(new Insets(5));
		passwordContent.getChildren().addAll(pass1, passwordField, pass2, passwordField2, changePwBtn);
		return passwordContent;
	}

	@Override
	public void refresh() {
		passwordField.clear();
		passwordField2.clear();
		usernameField.clear();
		infoLbl.setText("");
		idLbl.setText(LanguageUtil.translate("Your Id: ") + controller.getUserId());
	}

	@Override
	public Node getContent() {
		return content;
	}

}
