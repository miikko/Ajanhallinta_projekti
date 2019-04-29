package application;


import controllers.GUI_Controller;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Creates the main screen that the user sees after logging in.<br>
 * Calls element builder functions and sets the contents to default.<br>
 * Additionally adds the main screen to controllers screen pool so it can be
 * chosen later.
 * 
 * @author miikk & MrJoXuX & JP
 */
public class MainScreen extends BorderPane {
	private VBox navBar;
	private VBox defaultContent;
	private Label welcomeLbl;
	private MenuButton optionMenu;
	private VBox accountContainer;
	private Label infoLabel;
	private TextField usernameField;
	private HistoryContainer historyContainer;
	private GroupContainer groupContainer;
	private GUI_Controller controller;

	public MainScreen(GUI_Controller controller) {
		this.controller = controller;
		create();
		StringProperty welcomeProp = new SimpleStringProperty();
		welcomeProp.bind(Bindings.concat("Welcome ").concat(controller.getUsernameProperty()));
		welcomeLbl.textProperty().bind(welcomeProp);
		controller.addScreen("Main", this);
	}

	private void create() {
		historyContainer = new HistoryContainer(controller);
		groupContainer = new GroupContainer(controller);
		createDefaultContent();
		createNavBar();
		createOptionMenu();
		createAccountContainer();
		this.setLeft(navBar);
		this.setCenter(defaultContent);
		this.setRight(optionMenu);
	}

	/**
	 * Changes contents to selection and cleans the previous content.
	 * 
	 * @param selection
	 */
	private void updateCenter(Node selection) {
		if (this.getCenter() == accountContainer) {
			infoLabel.setText("");
			usernameField.clear();
		}
		this.setCenter(selection);
	}

	/**
	 * Creates a MenuButton for various options such as exiting the application and
	 * logging out.<br>
	 * On press, the controller is called to handle the action.
	 */
	private void createOptionMenu() {
		optionMenu = new MenuButton("Options");
		optionMenu.setId("optionMenu");

		MenuItem accountItem = new MenuItem("Account");
		accountItem.setId("accountItem");
		accountItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				updateCenter(accountContainer);
			}
		});
		MenuItem logoutItem = new MenuItem("Logout");
		logoutItem.setId("logoutItem");
		logoutItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.handleLogout();
				controller.activateScreen("Login");
			}
		});
		MenuItem quitItem = new MenuItem("Quit");
		quitItem.setId("quitItem");
		quitItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.handleExit();
			}
		});
		optionMenu.getItems().addAll(accountItem, logoutItem, quitItem);
	}

	private void createAccountContainer() {
		Label idLbl = new Label("Your Id: " + controller.getUserId()); 
		infoLabel = new Label("");
		Label usern = new Label("New username");
		Label pass1 = new Label("New password");
		Label pass2 = new Label("Type same password");
		usernameField = new TextField();
		PasswordField passwordField = new PasswordField();
		PasswordField passwordField2 = new PasswordField();
		Button changeUnameBtn = new Button("Change username");
		Button changePwBtn = new Button("Change password");

		changeUnameBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String username = usernameField.getText();
				if (controller.handleUsernameChange(username)) {
					infoLabel.setText("Vaihto onnistui!");
					usernameField.clear();
				} else {
					infoLabel.setText("Vaihto epäonnistui.");
				}
			}
		});

		changePwBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String setPass1 = passwordField.getText();
				String setPass2 = passwordField2.getText();
				if (controller.handlePasswordChange(setPass1, setPass2)) {
					infoLabel.setText("Vaihto onnistui!");
				} else {
					infoLabel.setText("Vaihto epäonnistui.");
				}
				passwordField.clear();
				passwordField2.clear();
			}
		});

		accountContainer = new VBox();
		accountContainer.setSpacing(10);
		accountContainer.setAlignment(Pos.CENTER);
		accountContainer.getChildren().addAll(idLbl, usern, usernameField, pass1, passwordField, pass2, passwordField2,
				changeUnameBtn, changePwBtn, infoLabel);
	}

	/**
	 * Initializes the navigation bar that can be seen on the left side of the
	 * screen once a user has logged in.<br>
	 * 
	 */
	private void createNavBar() {
		navBar = new VBox();
		Button defaultBtn = new Button("Main menu");
		defaultBtn.setOnAction(createNavBarBtnHandler(defaultContent));
		Button historyBtn = new Button("History");
		historyBtn.setOnAction(createNavBarBtnHandler(historyContainer));
		Button groupsBtn = new Button("Groups");
		groupsBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				groupContainer.refresh();
				updateCenter(groupContainer);
			}
		});
		navBar.getChildren().addAll(defaultBtn, historyBtn, groupsBtn);
	}
	
	private EventHandler<ActionEvent> createNavBarBtnHandler(Node destination) {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				updateCenter(destination);
			}
		};
	}

	/**
	 * Creates the greeting a user receives when logging in.<br>
	 * Also contains a button that starts the recorder service.
	 */
	private void createDefaultContent() {
		defaultContent = new VBox();
		welcomeLbl = new Label("");
		Stopwatch stopwatch = new Stopwatch(controller);
		defaultContent.setAlignment(Pos.CENTER);
		defaultContent.getChildren().addAll(welcomeLbl, stopwatch);
	}
}
