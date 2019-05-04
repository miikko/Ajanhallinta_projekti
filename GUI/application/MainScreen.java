package application;


import controllers.GUI_Controller;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
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
	private MenuButton optionMenu;
	private VBox accountContainer;
	private Label infoLabel;
	private TextField usernameField;
	private HistoryContainer historyContainer;
	private GroupContainer groupContainer;
	private RestrictionContainer restrictContainer;
	private GUI_Controller controller;

	public MainScreen(GUI_Controller controller) {
		this.controller = controller;
		create();
		controller.addScreen("Main", this);
	}

	private void create() {
		historyContainer = new HistoryContainer(controller);
		groupContainer = new GroupContainer(controller);
		restrictContainer = new RestrictionContainer(controller);
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
					infoLabel.setText("Username was changed successfully!");
					usernameField.clear();
				} else {
					infoLabel.setText("Failed to change username.");
				}
			}
		});

		changePwBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String setPass1 = passwordField.getText();
				String setPass2 = passwordField2.getText();
				if (controller.handlePasswordChange(setPass1, setPass2)) {
					infoLabel.setText("Password was changed successfully!");
				} else {
					infoLabel.setText("Failed to change password.");
				}
				passwordField.clear();
				passwordField2.clear();
			}
		});

		accountContainer = new VBox();
		VBox areaMiddle = new VBox();
        areaMiddle.setBackground(Background.EMPTY);
        areaMiddle.setPrefSize(100, 200);
        areaMiddle.setSpacing(5);
        areaMiddle.setAlignment(Pos.CENTER);
        String style = "-fx-background-color: rgba(167, 197, 204, 0.5);";
        areaMiddle.setStyle(style);
        areaMiddle.setPadding(new Insets(5));
        areaMiddle.getChildren().addAll(usern, usernameField, changeUnameBtn);
        
        VBox areaBottomMiddle = new VBox();
        areaBottomMiddle.setBackground(Background.EMPTY);
        areaBottomMiddle.setPrefSize(100, 250);
        areaBottomMiddle.setSpacing(5);
        areaBottomMiddle.setStyle(style);
        areaBottomMiddle.setAlignment(Pos.CENTER);
        areaBottomMiddle.setPadding(new Insets(5));
        areaBottomMiddle.getChildren().addAll(pass1, passwordField, pass2, passwordField2,
				changePwBtn);
		accountContainer.setSpacing(20);
		accountContainer.setAlignment(Pos.CENTER);
		accountContainer.getChildren().addAll(idLbl, areaMiddle, areaBottomMiddle, infoLabel);
	}

	/**
	 * Initializes the navigation bar that can be seen on the left side of the
	 * screen once a user has logged in.<br>
	 * 
	 */
	private void createNavBar() {
		navBar = new VBox();
		navBar.setId("naviBar");
		Button defaultBtn = new Button("Main menu");
		defaultBtn.setId("mainmenuBtn");
		defaultBtn.setOnAction(createNavBarBtnHandler(defaultContent));
		Button historyBtn = new Button("History");
		historyBtn.setId("historyBtn");
		historyBtn.setOnAction(createNavBarBtnHandler(historyContainer));
		Button groupsBtn = new Button("Groups");
		groupsBtn.setId("groupsBtn");
		groupsBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				groupContainer.refresh();
				updateCenter(groupContainer);
			}
		});
		Button restrictBtn = new Button("Restrictions");
		restrictBtn.setOnAction((ActionEvent event) -> {
			restrictContainer.refresh();
			updateCenter(restrictContainer);
		});
		navBar.getChildren().addAll(defaultBtn, historyBtn, groupsBtn, restrictBtn);
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
		Label welcomeLbl = new Label("");
		StringProperty welcomeProp = new SimpleStringProperty();
		welcomeProp.bind(Bindings.concat("Welcome ").concat(controller.getUsernameProperty()));
		welcomeLbl.textProperty().bind(welcomeProp);
		Stopwatch stopwatch = new Stopwatch(controller);
		defaultContent.setAlignment(Pos.CENTER);
		defaultContent.getChildren().addAll(welcomeLbl, stopwatch);
	}
}
