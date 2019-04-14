package application;

import java.util.HashSet;
import java.util.Locale;

import controllers.GUI_Controller;
import database.Sitting;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Class is designed to show GUI content to user. <br>
 * View part in the MVC-framework
 * 
 * @author miikko & MrJoXuX & JP
 * @since 11/03/2019
 */
//TODO: Find a proper place to create screens
public class View extends Application {

	private GUI_Controller controller;
	private Scene scene;
	// Login screen variables
	private BorderPane loginScreen;
	private VBox tfContainer;
	private HBox btnContainer;
	private TextField usernameTF;
	private PasswordField passwordTF;
	private Label inputInfoLbl;
	// Main screen variables
	private BorderPane mainScreen;
	private VBox navBar;
	private VBox defaultContent;
	private Label welcomeLbl;
	private MenuButton optionMenu;
	private boolean recording;
	// Stopwatch variables
	private Stopwatch stopwatch;
	// Chart variables
	private BorderPane chartContainer;
	private ObservableList<String> chartTypes;
	private StackPane pieChart;
	private StackPane barChart;
	// Date picker variables
	private DatePicker datePicker;
	// Account variables
	private VBox accountContainer;

	@Override
	public void start(Stage primaryStage) {
		try {
			controller = new GUI_Controller(this);
			createLoginScreen(5);
			scene = new Scene(loginScreen, 1000, 700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene.getStylesheets().add(getClass().getResource("stopwatch.css").toExternalForm());
			primaryStage.setTitle("Ajanhallintapalvelu");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() {

	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		launch(args);
	}

	/**
	 * Creates the main screen that the user sees after logging in.<br>
	 * Calls element builder functions and sets the contents to default.<br>
	 * Additionally adds the main screen to controllers screen pool so it can be chosen
	 * later.
	 */
	private void createMainScreen() {
		mainScreen = new BorderPane();
		createChartContainer();;
		stopwatch = new Stopwatch();
		createDatePicker();
		createNavBar();
		createDefaultContent();
		createAccountContainer();
		createOptionMenu();
		mainScreen.setLeft(navBar);
		mainScreen.setCenter(defaultContent);
		mainScreen.setRight(optionMenu);
		controller.addScreen("Main", mainScreen);
	}

	/**
	 * Changes main screen contents to selection.
	 * 
	 * @param selection
	 */
	private void updateMainScreen(String selection) {
		switch (selection) {
		case "Charts":
			mainScreen.setCenter(chartContainer);
			break;
		case "Stopwatch":
			mainScreen.setCenter(stopwatch);
			break;
		case "DatePick":
			mainScreen.setCenter(datePicker);
			break;
		case "Default":
			mainScreen.setCenter(defaultContent);
		case "Account":
			mainScreen.setCenter(accountContainer);
			break;
		default:
			break;
		}
	}

	/**
	 * Creates the login screen that is the first screen the user sees when logging
	 * in.<br>
	 * This screen contains 2 text fields for username and password and 2 buttons
	 * for logging in and registering.<br>
	 * Also adds the login screen to controllers screen pool so it can be chosen
	 * later, for example when logging out.
	 * 
	 * @param spacing
	 */
	private void createLoginScreen(int spacing) {
		loginScreen = new BorderPane();
		createLoginTextFields(spacing);
		createLoginBtnContainer(spacing);
		loginScreen.setCenter(tfContainer);
		loginScreen.setBottom(btnContainer);
		controller.addScreen("Login", loginScreen);
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
				if (controller.handleLogin(username, password)) {
					createMainScreen();
					controller.activateScreen("Main");
				} else {
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
				if (controller.handleRegister(username, password)) {
					createMainScreen();
					createAndDisplayPopup("Registration successful!", "Main");
				} else {
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
	}

	/**
	 * Creates and shows a popup with the given message and a confirm button.<br>
	 * Clicking the button hides the popup and activates the given screen.
	 * 
	 * @param message
	 * @param transitionScreenName
	 */
	// TODO: Find a more elegant way for creating a popup
	private void createAndDisplayPopup(String message, String transitionScreenName) {
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		VBox popup = new VBox();
		Button confButton = new Button("Confirm");
		confButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				popupStage.hide();
				controller.activateScreen(transitionScreenName);
			}
		});
		popup.getChildren().addAll(new Label(message), confButton);
		popup.setAlignment(Pos.CENTER);
		popup.setPadding(new Insets(15));
		Scene popupScene = new Scene(popup);
		popupScene.getStylesheets().add(getClass().getResource("stopwatch.css").toExternalForm());
		popupStage.setScene(popupScene);
		popupStage.show();
	}

	/**
	 * Creates a MenuButton for various options such as exiting the application and
	 * logging out.<br>
	 * On press, the controller is called to handle the action.
	 */
	private void createOptionMenu() {
		optionMenu = new MenuButton("Options");

		MenuItem accountItem = new MenuItem("Account");
		accountItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				updateMainScreen("Account");
			}
		});
		MenuItem logoutItem = new MenuItem("Logout");
		logoutItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				controller.handleLogout();
				controller.activateScreen("Login");
			}
		});
		MenuItem quitItem = new MenuItem("Quit");
		quitItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.handleExit((Stage) scene.getWindow());
			}
		});
		optionMenu.getItems().addAll(accountItem, logoutItem, quitItem);
	}

	private void createAccountContainer() {
		Label infoLabel = new Label("");
		Label usern = new Label("New username");
		Label pass1 = new Label("New password");
		Label pass2 = new Label("Type same password");
		TextField usernameField = new TextField();
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
		accountContainer.getChildren().addAll(usern, usernameField, pass1, passwordField, pass2, passwordField2, changeUnameBtn,
				changePwBtn, infoLabel);
	}

	/**
	 * Initializes the navigation bar that can be seen on the left side of the
	 * screen once a user has logged in.<br>
	 * Creates both the spacing and the buttons included to the bar.
	 * 
	 */
	private void createNavBar() {
		navBar = new VBox();
		Button defaultBtn = new Button("Main menu");
		defaultBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				updateMainScreen("Default");
			}
		});
		Button swBtn = new Button("Stopwatch");
		swBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				updateMainScreen("Stopwatch");
			}
		});
		Button chartBtn = new Button("Charts");
		chartBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				updateMainScreen("Charts");
			}
		});
		Button dateBtn = new Button("History");
		dateBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				updateMainScreen("DatePick");
			}
		});
		navBar.getChildren().addAll(defaultBtn, swBtn, chartBtn, dateBtn);
	}

	/**
	 * Creates the greeting a user receives when logging in.<br>
	 * Also contains a button that starts the recorder service.
	 */
	private void createDefaultContent() {
		defaultContent = new VBox();
		String name = controller.getUserName();
		welcomeLbl = new Label("Welcome " + name);
		Button recBtn = new Button("Start recording");
		recBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (recording) {
					recBtn.setText("Start recording");
					controller.stopRecording();
					recording = false;
				} else {
					recBtn.setText("Stop recording");
					controller.startRecording();
					recording = true;
				}
			}
		});
		defaultContent.setAlignment(Pos.CENTER);
		defaultContent.getChildren().addAll(welcomeLbl, recBtn);
	}

	/**
	 * Calls the chart creation methods and additionally creates a BorderPane
	 * container for the created charts.<br>
	 * The container also contains a dropdown menu for selecting charts.
	 */
	private void createChartContainer() {
		chartContainer = new BorderPane();
		chartTypes = FXCollections.observableArrayList();
		pieChart = BarChartFactory.getInstance().createChart(new HashSet<Sitting>());
		barChart = PieChartFactory.getInstance().createChart(new HashSet<Sitting>());
		chartTypes.add("Pie chart");
		chartTypes.add("Bar chart");
		final ComboBox<String> comboBox = new ComboBox<>(chartTypes);
		comboBox.setPromptText("Select chart type");
		comboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if (arg2.equals("Pie chart")) {
					chartContainer.setCenter(pieChart);
				} else if (arg2.equals("Bar chart")) {
					chartContainer.setCenter(barChart);
				}
			}
		});
		chartContainer.setTop(comboBox);
	}

	private void createDatePicker() {
		VBox vB = new VBox(20);

		datePicker = new DatePicker();

		GridPane gPane = new GridPane();
		gPane.setHgap(10);
		gPane.setVgap(10);

		Label infoLabel = new Label("Select a past date:");
		gPane.add(infoLabel, 0, 0);

		GridPane.setHalignment(infoLabel, HPos.CENTER);
		gPane.add(datePicker, 0, 1);
		vB.getChildren().add(gPane);
	}

	public void setRoot(Pane screen) {
		scene.setRoot(screen);
	}
}
