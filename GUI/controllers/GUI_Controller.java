package controllers;

import Models.Charts;
import Models.Login;
import Models.Stopwatch;
import application.View;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GUI_Controller {
	
	private View view;
	private Stopwatch sw;
	private Login login;
	private Charts charts;
	
	public GUI_Controller(View view) {
		this.view = view;
		sw = new Stopwatch();
		login = new Login();
		charts = new Charts();
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
	
	public StackPane getPieChart() {
		return charts.getPieChart();
	}
}
