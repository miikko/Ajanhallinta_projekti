package application;

import java.util.HashSet;

import controllers.GUI_Controller;
import database.Sitting;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Creates the main screen that the user sees after logging in.<br>
 * Calls element builder functions and sets the contents to default.<br>
 * Additionally adds the main screen to controllers screen pool so it can be
 * chosen later.
 * 
 * @author miikk & MrJoXuX & JP
 */
class MainScreen extends BorderPane {
	private Stopwatch stopwatch;
	private BorderPane chartContainer;
	private ObservableList<String> chartTypes;
	private DatePicker calendar;
	private VBox navBar;
	private VBox defaultContent;
	private Label welcomeLbl;
	private MenuButton optionMenu;
	private VBox accountContainer;
	private Label infoLabel;
	private TextField usernameField;
	private boolean recording;
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
		createChartContainer();
		stopwatch = new Stopwatch();
		createDatePicker();
		createNavBar();
		createDefaultContent();
		createAccountContainer();
		createOptionMenu();
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

		MenuItem accountItem = new MenuItem("Account");
		accountItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				updateCenter(accountContainer);
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
				controller.handleExit();
			}
		});
		optionMenu.getItems().addAll(accountItem, logoutItem, quitItem);
	}

	private void createAccountContainer() {
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
		accountContainer.getChildren().addAll(usern, usernameField, pass1, passwordField, pass2, passwordField2,
				changeUnameBtn, changePwBtn, infoLabel);
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
				updateCenter(defaultContent);
			}
		});
		Button swBtn = new Button("Stopwatch");
		swBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				updateCenter(stopwatch);
			}
		});
		Button chartBtn = new Button("Charts");
		chartBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				updateCenter(chartContainer);
			}
		});
		Button dateBtn = new Button("History");
		dateBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				updateCenter(calendar);
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
		welcomeLbl = new Label("");
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
		StackPane pieChart = BarChartFactory.getInstance().createChart(new HashSet<Sitting>());
		StackPane barChart = PieChartFactory.getInstance().createChart(new HashSet<Sitting>());
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

		calendar = new DatePicker();

		GridPane gPane = new GridPane();
		gPane.setHgap(10);
		gPane.setVgap(10);

		Label infoLabel = new Label("Select a past date:");
		gPane.add(infoLabel, 0, 0);

		GridPane.setHalignment(infoLabel, HPos.CENTER);
		gPane.add(calendar, 0, 1);
		vB.getChildren().add(gPane);
	}
}
