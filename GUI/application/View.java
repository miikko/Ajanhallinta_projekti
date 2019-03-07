package application;
	
import java.util.List;

import controllers.GUI_Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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


public class View extends Application {
	
	private GUI_Controller controller;
	private Scene scene;
	//Login screen variables
	private BorderPane loginScreen;
	private VBox tfContainer;
	private HBox btnContainer;
	private TextField usernameTF;
	private PasswordField passwordTF;
	private Label popUp;
	//Main screen variables
	private BorderPane mainScreen;
	private VBox navBar;
	private Label welcomeLbl;
	//Stopwatch variables
	private VBox stopwatch;
	private int  hrs = 0, mins = 0, secs = 0, millis = 0;
	private boolean sos = true;
	//Chart variables
	private StackPane pieChart;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			controller = new GUI_Controller(this);
			createLoginScreen(5);
			scene = new Scene(loginScreen,1000,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene.getStylesheets().add(getClass().getResource("stopwatch.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void init() {
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void createMainScreen() {
		mainScreen = new BorderPane();
		createPieChart();
		createStopwatch();
		createNavBar();
		createWelcomeLabel();
		mainScreen.setLeft(navBar);
		mainScreen.setCenter(welcomeLbl);
		controller.addScreen("Main", mainScreen);
	}
	
	private void updateMainScreen(String selection) {
		switch (selection) {
		case "PieChart":
			mainScreen.setCenter(pieChart);
			break;
		case "Stopwatch":
			mainScreen.setCenter(stopwatch);
			break;
		case "Default":
			mainScreen.setCenter(welcomeLbl);
			break;
		default:
			break;
		}
	}
	
	private void createLoginScreen(int spacing) {
		loginScreen = new BorderPane();
		createLoginTextFields(spacing);
		createLoginBtnContainer(spacing);
		loginScreen.setCenter(tfContainer);
		loginScreen.setBottom(btnContainer);
		controller.addScreen("Login", loginScreen);
	}
	
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
					//TODO: Transition to main screen
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
					//TODO: Display an alert with confirmation btn that a new user was created
					//TODO: Transition to main screen
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
		Button sButton = new Button("Start");
		sButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(sos) {
            		timeline.play();
            		sos = false;
            		sButton.setText("Stop");
            	} else {
            		timeline.pause();
            		sos = true;
            		sButton.setText("Start");
            	}
            }
        });
		Button rButton = new Button("Reset");
		rButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	mins = 0;
            	secs = 0;
            	millis = 0;
            	timeline.pause();
            	timerText.setText("00:00:00");
            	if(!sos) {
            		sos = true;
            		sButton.setText("Start");
            	}
            }
        });
		timerText.setId("timerNum");

		HBox swBtnContainer = new HBox(30);
		swBtnContainer.setAlignment(Pos.CENTER);
		swBtnContainer.getChildren().addAll(sButton, rButton);
		stopwatch = new VBox(30);
		stopwatch.setAlignment(Pos.CENTER);
		stopwatch.getChildren().addAll(timerText, swBtnContainer);
	}
	
	private void change(Text text) {
		if(millis == 1000) {
			secs++;
			millis = 0;
		}
		if(secs == 60) {
			mins++;
			secs = 0;
		}
		if(mins == 60) {
			hrs++;
			mins = 0;
		}
		text.setText((((hrs/10) == 0) ? "0" : "") + hrs + ":" + (((mins/10) == 0) ? "0" : "") + mins + ":"
				 + (((secs/10) == 0) ? "0" : "") + secs);
    }
	
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
				updateMainScreen("PieChart");
			}
		});
		navBar.getChildren().addAll(defaultBtn, swBtn, chartBtn);
	}
	
	private void createWelcomeLabel() {
		String name = controller.getUserName();
		welcomeLbl = new Label("Welcome " + name);
	}
	
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
	
	public void setRoot(Pane screen) {
		scene.setRoot(screen);
	}
}

