package controllers;

import Models.Login;
import Models.Stopwatch;
import application.View;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GUI_Controller {
	
	private View view;
	private Stopwatch sw;
	private Login login;
	
	public GUI_Controller(View view) {
		this.view = view;
		sw = new Stopwatch();
		login = new Login();
	}
	
	public VBox getStopWatch() {
		return sw.getVBox();
	}
	
	public HBox[] getLoginContainer(int spacing) {
		HBox[] loginContainer = new HBox[2];
		HBox textFields = login.getLoginTextFields(spacing);
		HBox btnContainer = login.getBtnContainer(spacing);
		loginContainer[0] = textFields;
		loginContainer[1] = btnContainer;
		return loginContainer;
	}
	
	public VBox getStopwatch() {
		return sw.getVBox();
	}
}
