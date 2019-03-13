package application;

import controllers.GUI_Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.Recorder;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Class is designed to show GUI content to user. <br>
 * View part in the MVC-framework
 * 
 * @author miikko & MrJoXuX
 * @since 11/03/2019
 */
public class View extends Application {

	private GUI_Controller controller;
	private Scene scene;
	// Login screen variables
	private BorderPane loginScreen;
	private VBox tfContainer;
	private HBox btnContainer;
	private TextField usernameTF;
	private PasswordField passwordTF;
	private Label popUp;
	// Main screen variables
	private BorderPane mainScreen;
	private VBox navBar;
	private VBox defaultContent;
	private Label welcomeLbl;
	private boolean recording;
	// Stopwatch variables
	private VBox stopwatch;
	private int hrs = 0, mins = 0, secs = 0, millis = 0;
	private boolean pause = true;
	// Chart variables
	private StackPane pieChart;
	private StackPane barChart;

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
		launch(args);
	}

	/**
	 * Creates the main screen that the user sees after logging in.<br>
	 * Calls element builder functions and sets the contents to default.<br>
	 * Also adds the main screen to controllers screen pool so it can be chosen
	 * later.
	 */
	private void createMainScreen() {
		mainScreen = new BorderPane();
		createPieChart();
		createBarChart();
		createStopwatch();
		createNavBar();
		createDefaultContent();
		mainScreen.setLeft(navBar);
		mainScreen.setCenter(defaultContent);
		controller.addScreen("Main", mainScreen);
	}

	/**
	 * Changes main screen contents to selection.
	 * 
	 * @param selection
	 */
	private void updateMainScreen(String selection) {
		switch (selection) {
		case "PieChart":
			mainScreen.setCenter(pieChart);
			break;
		case "BarChart":
			mainScreen.setCenter(barChart);
			break;
		case "Stopwatch":
			mainScreen.setCenter(stopwatch);
			break;
		case "Default":
			mainScreen.setCenter(defaultContent);
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
				if (!popUp.getText().equals("")) {
					popUp.setText("");
				}
			}
		};
		tfContainer = new VBox();
		popUp = new Label("");
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
		tfContainer.getChildren().addAll(popUp, subContainer);
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
				System.out.println("Username: " + username + ", password: " + password);
				if (controller.handleLogin(username, password)) {
					// TODO: Transition to main screen
					createMainScreen();
					controller.activateScreen("Main");
				} else {
					popUp.setText("Invalid username and/or password.");
				}
			}
		});
		Button regBtn = new Button("Register");
		regBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String username = usernameTF.getText();
				String password = passwordTF.getText();
				if (controller.handleRegister(username, password)) {
					// TODO: Display an alert with confirmation btn that a new user was created
					// TODO: Transition to main screen
					createMainScreen();
				} else {
					popUp.setText("Selected username is already taken. Please choose another one.");
				}
			}

		});
		HBox.setMargin(loginBtn, new Insets(spacing));
		HBox.setMargin(regBtn, new Insets(spacing));
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.setStyle("-fx-border-color: black");
		btnContainer.getChildren().addAll(loginBtn, regBtn);
	}

	/**
	 * Creates a stopwatch that can be used to track teh screentime of a certain
	 * application.<br>
	 * Includes all the buttons and methods for the stopwatch to work independently.
	 * 
	 */
	private void createStopwatch() {
		Text timerText = new Text("00:00:00");
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				change(timerText);
				millis++;
			}
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(false);
		Button startButton = new Button("Start");
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (pause) {
					timeline.play();
					pause = false;
					startButton.setText("Stop");
				} else {
					timeline.pause();
					pause = true;
					startButton.setText("Start");
				}
			}
		});
		Button resetButton = new Button("Reset");
		resetButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mins = 0;
				secs = 0;
				millis = 0;
				timeline.pause();
				timerText.setText("00:00:00");
				if (!pause) {
					pause = true;
					startButton.setText("Start");
				}
			}
		});
		timerText.setId("timerNum");

		HBox swBtnContainer = new HBox(30);
		swBtnContainer.setAlignment(Pos.CENTER);
		swBtnContainer.getChildren().addAll(startButton, resetButton);
		stopwatch = new VBox(30);
		stopwatch.setAlignment(Pos.CENTER);
		stopwatch.getChildren().addAll(timerText, swBtnContainer);
	}

	private void change(Text text) {
		if (millis == 1000) {
			secs++;
			millis = 0;
		}
		if (secs == 60) {
			mins++;
			secs = 0;
		}
		if (mins == 60) {
			hrs++;
			mins = 0;
		}
		text.setText((((hrs / 10) == 0) ? "0" : "") + hrs + ":" + (((mins / 10) == 0) ? "0" : "") + mins + ":"
				+ (((secs / 10) == 0) ? "0" : "") + secs);
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
		/*
		 * Button chartBtn = new Button("Charts"); chartBtn.setOnAction(new
		 * EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent arg0) {
		 * updateMainScreen("PieChart"); } });
		 */
		Button pieChartBtn = new Button("Pie charts");
		pieChartBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				updateMainScreen("PieChart");
			}
		});
		Button barChartBtn = new Button("Bar charts");
		barChartBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				updateMainScreen("BarChart");
			}
		});
		navBar.getChildren().addAll(defaultBtn, swBtn, pieChartBtn, barChartBtn);
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
		Recorder rec = new Recorder();
		Service<Void> recService = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						while (recording) {
							Platform.runLater(() -> {
								welcomeLbl.setText("Active window name: " + rec.getActiveProgramDescription());
							});
							Thread.sleep(1000);
						}
						Platform.runLater(() -> {
							welcomeLbl.setText("Welcome " + name);
						});
						return null;
					}
				};
			}

		};
		recBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (recording) {
					recBtn.setText("Start recording");
					recording = false;
				} else {
					recService.reset();
					recService.start();
					recBtn.setText("Stop recording");
					recording = true;
				}
			}
		});
		defaultContent.setAlignment(Pos.CENTER);
		defaultContent.getChildren().addAll(welcomeLbl, recBtn);
	}

	/**
	 * Initializes the base for a pie chart that can be used to show the elapsed
	 * screen time of a user.<br>
	 * Includes the basic model of the chart and the information of the given day.
	 */
	private void createPieChart() {
		pieChart = new StackPane();
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data("YouTube", 13),
				new PieChart.Data("Twitch", 25), new PieChart.Data("Netflix", 10), new PieChart.Data("ViaPlay", 22),
				new PieChart.Data("Ruutu", 30));
		final PieChart chart = new PieChart(pieChartData);
		chart.setTitle("Used time on different applications");
		final Label caption = new Label("");
		caption.setTextFill(Color.BLACK);
		caption.setStyle("-fx-font: 20 arial;");
		for (final PieChart.Data data : chart.getData()) {
			data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					caption.setTranslateX(e.getSceneX() - caption.getLayoutX() - 10);
					caption.setTranslateY(e.getSceneY() - caption.getLayoutY() - 10);
					caption.setText(data.getPieValue() + "%");
				}
			});
		}
		chart.setLabelLineLength(10);
		chart.setLegendSide(Side.LEFT);
		pieChart.getChildren().addAll(chart, caption);
	}

	/**
	 * Initializes the base for a bar chart that can be used to show the elapsed
	 * screen time of a user.<br>
	 * Includes the basic model of the chart and the information of the given day or
	 * week.
	 */
	private void createBarChart() {

		barChart = new StackPane();

		final String monday = "Monday";
		final String tuesday = "Tuesday";
		final String wednesday = "Wednesday";
		final String thursday = "Thursday";
		final String friday = "Friday";
		final String saturday = "Saturday";
		final String sunday = "Sunday";

		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		final BarChart<String, Number> bChart = new BarChart<String, Number>(xAxis, yAxis);
		bChart.setTitle("Used time on different applications");
		xAxis.setLabel("Day of the week");
		yAxis.setLabel("Hours");

		XYChart.Series<String, Number> platform1 = new XYChart.Series<>();
		platform1.setName("Netflix");
		platform1.getData().add(new XYChart.Data<>(monday, 7.2));
		platform1.getData().add(new XYChart.Data<>(tuesday, 8.7));
		platform1.getData().add(new XYChart.Data<>(wednesday, 3.0));
		platform1.getData().add(new XYChart.Data<>(thursday, 4.4));
		platform1.getData().add(new XYChart.Data<>(friday, 5.2));
		platform1.getData().add(new XYChart.Data<>(saturday, 1.1));
		platform1.getData().add(new XYChart.Data<>(sunday, 1.1));

		XYChart.Series<String, Number> platform2 = new XYChart.Series<>();
		platform2.setName("Twitch");
		platform2.getData().add(new XYChart.Data<>(monday, 2.2));
		platform2.getData().add(new XYChart.Data<>(tuesday, 3.2));
		platform2.getData().add(new XYChart.Data<>(wednesday, 4.8));
		platform2.getData().add(new XYChart.Data<>(thursday, 1.1));
		platform2.getData().add(new XYChart.Data<>(friday, 0.9));
		platform2.getData().add(new XYChart.Data<>(saturday, 7.2));
		platform2.getData().add(new XYChart.Data<>(sunday, 2.2));
		XYChart.Series<String, Number> platform3 = new XYChart.Series<>();
		platform3.setName("YouTube");
		platform3.getData().add(new XYChart.Data<>(monday, 4.4));
		platform3.getData().add(new XYChart.Data<>(tuesday, 3.3));
		platform3.getData().add(new XYChart.Data<>(wednesday, 7.1));
		platform3.getData().add(new XYChart.Data<>(thursday, 4.9));
		platform3.getData().add(new XYChart.Data<>(friday, 9.2));
		platform3.getData().add(new XYChart.Data<>(saturday, 6.8));
		platform3.getData().add(new XYChart.Data<>(sunday, 4.4));

		bChart.getData().addAll(platform1, platform2, platform3);

		barChart.getChildren().addAll(bChart);
	}

	public void setRoot(Pane screen) {
		scene.setRoot(screen);
	}
}
